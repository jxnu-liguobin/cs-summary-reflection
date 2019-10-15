---
title: actor消息传递的可靠性
categories:
  - Akka
tags: [Akka-actor入门]
description: 本章概述了actor消息传递的可靠性的一般性规则
---

* 目录
{:toc}

为了给下面的讨论提供一些上下文，请考虑一个跨越多个网络主机的应用程序。
无论是发送到本地JVM上的参与者还是向远程参与者发送，通信的基本机制是相同的，但是在传递延迟(可能还取决于网络链路的带宽和消息大小)和可靠性方面会有明显的差异。
在远程消息发送的情况下，需要执行更多的步骤，这意味着更多的步骤可能出错。
另一个方面是，本地发送将在同一个JVM中传递对消息的引用，对所发送的底层对象没有任何限制，而远程传输将限制消息大小。

编写actor的安全方式，是假设每一个通信可能是远程的，即做一个悲观的赌注。这意味着只依赖那些始终得到保证并在下文中详细讨论的属性。这在actor的实现中有一些开销。
如果您愿意牺牲充分的位置透明性-例如，在一组密切协作的actor的情况下-您可以始终将它们放在同一个JVM上，并在消息传递方面享有更严格的保证。

如有不理解可参看[官方文档 Message Delivery Reliability](https://doc.akka.io/docs/akka/current/general/message-delivery-reliability.html)


#### 一般性规则简述

这些是消息发送的规则(即tell或!方法，该方法也是ask(模式)下的规则)：

* 最多一次发送，即没有保证发送
* 消息排序，每个发送方->接收方
     
第一个规则通常也可以在其他actor实现中找到，而第二个规则是特定于Akka的。

#### 讨论：“最多一次”是什么意思

在描述传递机制的语义时，有三个基本类别：
* 最多一次发送意味着，对于发送给该actor的每一条消息，该消息只发送一次或根本不发送；更随意地说，这意味着消息可能丢失。
* 至少一次传递意味着，对于发送给该actor的每一条消息，可能会多次尝试发送，因此至少有一条成功；同样，以更随意的方式来说，这意味着消息可能会重复，但不会丢失。
* 一次传递意味着，对于发送给该actor的每一条消息，都会向接收方进行一次传递；该消息既不能丢失，也不能重复。

第一个是最低代价的-最高的性能，即最少的实施开销，因为它可以一劳永逸的方式完成，而无需在发送端或传输机制中保持状态。
第二个要求重试以抵消传输损失，这意味着在发送端保持状态并在接收端具有确认机制。
第三种是最昂贵的，并且因此具有最差的性能，因为除了第二种之外，它还要求状态保持在接收端，以过滤出重复传递的消息。

#### 讨论：为什么没有保证发送

问题的核心在于这样的保证究竟意味着什么：

* 消息是在网络上发送的吗？
* 消息被另一个主机接收了吗？
* 消息被放入目标actor的信箱中？
* 消息开始由目标actor处理了吗？
* 消息被目标actor成功地处理了吗？

其中每一个都有不同的挑战和成本，很明显，在某些条件下，任何消息传递库都无法满足要求；
例如，考虑一下可配置的邮信箱类型，一个有界的信箱将如何与第三点交互，甚至决定第五点的“成功”部分意味着什么。

按照同样的思路，[没有人需要可靠的消息传递](https://www.infoq.com/articles/no-reliable-messaging/)。对于发送者来说，了解交互是否成功的唯一有意义的方法是接收一条业务级别的确认消息，这不是akka可以自己弥补的。

Akka采用分布式计算，并通过消息传递使通信的错误性变得明确，因此它不试图欺骗和模拟泄漏的抽象。这是一个在Erlang中非常成功地使用的模型，并且要求用户围绕它设计他们的应用程序。您可以在[Erlang文档 第10.9节和第10.10节](http://www.erlang.org/faq/academic.html)中更多地了解这种方法。

关于这个问题的另一个角度是，通过仅提供基本保证，那些不需要更强可靠性的用例就无需支付实施成本。总是有可能在基本的基础上增加更强的可靠性，但是不可能追溯性地删除可靠性以获得更多性能。

#### 讨论：消息排序

更具体地说，规则是对于给定的一对actor，从第一个到第二个直接发送的消息不会被无序接收。这个词直接强调，此保证仅在与Tell运算符一起发送到最终目的地时才适用，而不在使用中介程序或其他消息分发功能时适用（除非另有说明）。
 
保证说明如下：

* Actor A1 sends messages M1, M2, M3 to A2
* Actor A3 sends messages M4, M5, M6 to A2

这意味着：

1. If M1 is delivered it must be delivered before M2 and M3
2. If M2 is delivered it must be delivered before M3
3. If M4 is delivered it must be delivered before M5 and M6
4. If M5 is delivered it must be delivered before M6
5. A2 can see messages from A1 interleaved with messages from A3
6. Since there is no guaranteed delivery, any of the messages may be dropped, i.e. not arrive at A2

但请注意，这一规则不具有传递性：

* Actor A sends message M1 to actor C
* Actor A then sends message M2 to actor B
* Actor B forwards message M2 to actor C
* Actor C may receive M1 and M2 in any order

这意味着M2不会被在M1被actor C收到之前被接收到(尽管其中任何一个都可能丢失)。当A、B和C在不同的网络主机上时，由于消息传递延迟不同，此顺序可能会被违反，请参阅下面的更多信息。

#### 失败的通信

上面讨论的排序保证只适用于actor之间的用户消息。actor的子程序的故障是由特定的系统消息传递的，这些消息相对于普通的用户消息而言是不被排序的。特别是：

* Child actor C sends message M to its parent P
* Child actor fails with failure F
* Parent actor P might receive the two events either in order M, F or F, M

原因是内部系统消息具有自己的邮箱，因此用户入队调用的顺序和系统消息不能保证其出队时间的顺序。

这里仅有一般性规则的说明，如有不理解或更多高级规则可参看[官方文档 Message Delivery Reliability](https://doc.akka.io/docs/akka/current/general/message-delivery-reliability.html)