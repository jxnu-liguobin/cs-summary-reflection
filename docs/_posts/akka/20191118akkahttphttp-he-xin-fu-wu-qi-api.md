---
title: HTTP核心服务端API
categories:
  - AkkaHTTP
tags:
  - akka-http 10.1.11文档
description: >-
  Akka-HTTP的核心服务器API主要实现了HTTP/1.1的基本功能，即akka-http-core本身没有实现高级的功能，诸如：请求路由，文件服务，压缩等
---

# 2019-11-18-AkkaHTTP-HTTP核心服务器API

* 目录

  {:toc}

> 版本 Akka-HTTP 10.1.10

这种设计使核心服务器API保持小巧轻便，易于理解和维护。HTTP/1.1基本功能如下：

* 连接管理 
* 解析和渲染消息和标头 
* 超时管理（用于请求和连接）
* 响应顺序（用于透明管道支持）

> 建议阅读[Implications of the streaming nature of Request/Response Entities](https://doc.akka.io/docs/akka-http/current/implications-of-streaming-http-entity.html)部分，因为它解释了底层的全堆栈流概念，当来自非“流优先”HTTP服务器的后台时，这可能是意外的。

## Streams 和 HTTP

Akka HTTP服务器是在[Streams](https://doc.akka.io/docs/akka/2.6.0/stream/index.html)之上实现的，在实现中以及在其API的所有级别上都大量使用它。

在连接级别，Akka HTTP提供与[Working with streaming IO](https://doc.akka.io/docs/akka/2.6.0/stream/stream-io.html)基本上相同的接口：套接字绑定表示为传入连接的流。该应用程序从该流源中提取连接，并为每个连接提供一个[Flow\[HttpRequest, HttpResponse, \_\]](https://doc.akka.io/api/akka/2.5.23/akka/stream/scaladsl/Flow.html)，以将请求“转换”为响应。

除了将绑定在服务器端的套接字视为[Source\[IncomingConnection, \_\]](https://doc.akka.io/api/akka/2.5.23/akka/stream/scaladsl/Source.html)，并将每个连接视为[Source\[HttpRequest, \_\]](https://doc.akka.io/api/akka/2.5.23/akka/stream/scaladsl/Source.html)和[Sink\[HttpResponse, \_\]](https://doc.akka.io/api/akka/2.5.23/akka/stream/scaladsl/Sink.html)，流抽象还存在于单个HTTP消息中：HTTP请求和响应的实体通常建模为[Source\[ByteString, \_\]](https://doc.akka.io/api/akka/2.5.23/akka/stream/scaladsl/Source.html)。另请参阅[HTTP模型](https://doc.akka.io/docs/akka-http/current/common/http-model.html)，以获取有关如何在Akka HTTP中表示HTTP消息的更多信息。

## 开始和停止

在最基本的级别上，通过调用[akka.http.scaladsl.Http](https://doc.akka.io/api/akka-http/10.1.10/akka/http/scaladsl/Http$.html)扩展的bind方法来绑定Akka HTTP服务器：

```scala
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._

implicit val system = ActorSystem()
implicit val materializer = ActorMaterializer()
implicit val executionContext = system.dispatcher

val serverSource: Source[Http.IncomingConnection, Future[Http.ServerBinding]] =
  Http().bind(interface = "localhost", port = 8080)
val bindingFuture: Future[Http.ServerBinding] =
  serverSource.to(Sink.foreach { connection => //源中每个链接
    println("Accepted new connection from " + connection.remoteAddress)
    //开始处理链接
  }).run()
```

Http\(\).bind方法的参数指定要绑定到的接口和端口，并在处理传入的HTTP连接时表现感兴趣。此外，该方法还允许定义套接字选项以及根据您的需要配置服务器的大量设置。

bind方法的结果是[Source\[Http.IncomingConnection\]](https://doc.akka.io/api/akka/2.5.23/akka/stream/scaladsl/Source.html)，它必须被应用程序耗尽以接受传入的连接。在将此源实现为处理管道的一部分之前，不会执行实际的绑定。万一绑定失败（例如因为端口已经忙），则实现的流将立即终止，并带有相应的异常。当传入连接源的订阅者取消其订阅时，绑定将被释放（即底层套接字未绑定）。或者，可以使用Http.ServerBinding实例的unbind\(\)方法，该方法是在连接源实现过程中创建的。 Http.ServerBinding还提供了一种方法来获取绑定套接字的实际本地地址，例如，该方法在绑定到端口零（从而让操作系统选择可用端口）时非常有用。

## 请求/响应周期

接受新连接后，它将以Http.IncomingConnection的形式发布，它由远程地址和方法组成，以提供[Flow\[HttpRequest, HttpResponse, \_\]](https://doc.akka.io/api/akka/2.5.23/akka/stream/scaladsl/Flow.html)用来处理通过此连接传入的请求。

通过使用处理程序调用handleWithXXX方法之一来处理请求，该处理程序可以是

* 一个用于handleWith的[Flow\[HttpRequest, HttpResponse, \_\]](https://doc.akka.io/api/akka/2.5.23/akka/stream/scaladsl/Flow.html)
* 一个用于handleWithSyncHandler的函数 HttpRequest =&gt; HttpResponse
* 一个用于handleWithAsyncHandler的函数 HttpRequest =&gt; Future\[HttpResponse\]

这是一个完整的示例：

```scala
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink

implicit val system = ActorSystem()
implicit val materializer = ActorMaterializer()
implicit val executionContext = system.dispatcher

val serverSource = Http().bind(interface = "localhost", port = 8080)

val requestHandler: HttpRequest => HttpResponse = {
  case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
    HttpResponse(entity = HttpEntity(
      ContentTypes.`text/html(UTF-8)`,
      "<html><body>Hello world!</body></html>"))

  case HttpRequest(GET, Uri.Path("/ping"), _, _, _) =>
    HttpResponse(entity = "PONG!")

  case HttpRequest(GET, Uri.Path("/crash"), _, _, _) =>
    sys.error("BOOM!")

  case r: HttpRequest =>
    r.discardEntityBytes() //耗尽传入的HTTP实体流这很重要！
    HttpResponse(404, entity = "Unknown resource!")
}

val bindingFuture: Future[Http.ServerBinding] =
  serverSource.to(Sink.foreach { connection =>
    println("Accepted new connection from " + connection.remoteAddress)

    connection handleWithSyncHandler requestHandler
    //这相当于下面调用
    // connection handleWith { Flow[HttpRequest] map requestHandler }
  }).run()
```

在此示例中，通过使用handleWithSyncHandler（或等效地，Akka Stream的map运算符）使用函数HttpRequest =&gt; HttpResponse转换请求流来处理请求。根据使用情况，可以使用Akka Stream的组合器来提供请求处理程序的许多其他方式。如果应用程序提供[FLow](https://doc.akka.io/api/akka/2.5.23/akka/stream/scaladsl/Flow.html)，则应用程序还有责任为每个请求生成一个准确的响应，并且响应的顺序与关联请求的顺序相匹配（如果启用了HTTP管道传输（其中多个传入请求的处理可能重叠），则这是相关的）。当依靠handleWithSyncHandler或handleWithAsyncHandler或map或mapAsync流运算符时，将自动满足此要求。

有关创建请求处理程序的更方便的高级DSL，请参见[“路由DSL概述”](https://doc.akka.io/docs/akka-http/current/routing-dsl/overview.html)。

### 流式请求/响应实体

通过[HttpEntity](https://doc.akka.io/api/akka-http/10.1.10/akka/http/scaladsl/model/HttpEntity.html)的子类支持HTTP消息实体的流传输。在接收请求以及在许多情况下构造响应时，该应用程序必须能够处理流式实体。有关替代方法的描述，请参见[HttpEntity](https://doc.akka.io/docs/akka-http/current/common/http-model.html#httpentity)。

如果您依赖Akka HTTP提供的[编组](https://doc.akka.io/docs/akka-http/current/common/marshalling.html)和/或[解组](https://doc.akka.io/docs/akka-http/current/common/unmarshalling.html)功能，则自定义类型与流实体之间的转换非常方便。这里是直接翻译的marshalling和unmarshalling。实际类似是Json的序列化这种可逆操作，同时需要依赖akka-http-spray-json。

### 断开连接

当处理[Flow](https://doc.akka.io/api/akka/2.5.23/akka/stream/scaladsl/Flow.html)取消其上游订阅或对等方关闭连接时（p2p对等网络），HTTP连接将关闭。通常更方便的替代方法是显式向HttpResponse添加Connection: close响应头。然后，此响应将是连接上的最后一个响应，并且在发送完连接后，服务器将主动关闭该连接。

如果请求实体已被取消（例如，通过将其附加到Sink.cancelled\(\)或仅被部分使用（例如，通过使用take组合器）），连接也将被关闭，为防止此行为，应通过将其附加到Sink.ignore\(\)来显式地耗尽它。

## 配置服务器端 HTTPS

有关在服务器端配置和使用HTTPS的详细文档，请参阅[服务器端HTTPS支持](https://doc.akka.io/docs/akka-http/current/server-side/server-https-support.html)。

## 独立的 HTTP 层用法

由于其基于响应流的性质，Akka HTTP层与底层TCP接口是完全可分离的。虽然在大多数应用程序中，这个“特性”并不重要，但在某些情况下，能够针对不是来自网络而是来自其他来源的数据“运行”HTTP层（可能还有更高的层）是非常有用的。潜在的可能有用的场景包括测试、调试或低级事件源（例如通过重播网络流量）。重播网络流的原文是replaying network traffic，这里可能有误。

在服务器端，独立HTTP层形成一个[BidiFlow](https://doc.akka.io/api/akka/2.5.23/akka/stream/scaladsl/BidiFlow.html)，其定义如下：

通过调用Http\(\).serverLayer方法的两个重载之一来创建Http.ServerLayer的实例，该方法还允许进行不同程度的配置。

## 控制服务器并行性

请求处理可以在两个轴上并行化，方法是并行处理多个连接，并依赖HTTP管道在一个连接上发送多个请求，而无需先等待响应。在这两种情况下，客户端都控制正在进行的请求数。为了防止太多的请求导致过载，Akka HTTP可以限制它并行处理的请求数。

要限制同时打开的连接数，请使用akka.http.server.max-connections设置。此设置适用于所有Http.bindAndHandle\* 方法。如果使用Http.bind，则传入连接由[Source\[IncomingConnection, ...\]](https://doc.akka.io/api/akka/2.5.23/akka/stream/scaladsl/Source.html)表示。使用Akka Stream的组合器应用背压以控制传入连接的流量，例如通过使用throttle或mapAsync。[背压 参考](https://www.zhihu.com/question/49618581/answer/117107570)

通常不鼓励使用HTTP管道传输（[大多数浏览器都禁用了HTTP管道传输](https://en.wikipedia.org/w/index.php?title=HTTP_pipelining&oldid=700966692#Implementation_in_web_browsers)），但是Akka HTTP完全支持HTTP管道传输。该限制适用于两个级别。首先，存在akka.http.server.pipelining-limit配置设置，该设置可防止将超过给定数量的未完成请求提供给用户提供的处理程序流。另一方面，处理程序流本身可以应用任何类型的限制。如果使用Http.bindAndHandleAsync入口点，则可以指定parallelism参数（默认为1，表示禁用流水线）来控制每个连接的并发请求数。如果使用Http.bindAndHandle或Http.bind，则用户提供的处理程序流将通过应用背压完全控制它同时接受多少个请求。在这种情况下，您可以使用Akka Stream的mapAsync组合器和给定的并行度来限制并发处理请求的数量。实际上，管道限制配置和手动控制流的请求方式中，约束程度越高的一个将决定如何处理一个连接上的并行请求。这里很绕口，大致理解是一个是基于通道全局的配置，一个是在服务器处理连接时手动设置的并行度参数。

## 在低级别 API 中处理 HTTP 服务器故障

在多种情况下，初始化或运行Akka HTTP服务器时可能会发生故障。默认情况下，Akka将记录所有这些故障，但是有时除了记录故障之外，还可能希望对故障做出反应，例如，通过关闭actor系统或明确通知某些外部监视端点。

创建和实例化HTTP Server时，有很多事情可能会失败（类似地，同样适用于普通流式Tcp\(\)服务器）。可能发生在堆栈不同层上的故障类型，从无法启动服务器开始，直到无法解组HttpRequest为止，故障的示例包括（从最外层到最内层）：

* 无法绑定到指定的地址/端口， 
* 接受新的IncomingConnections时失败，例如，当操作系统的文件描述符或内存不足时， 
* 处理连接时失败，例如，如果传入的[HttpRequest](https://doc.akka.io/api/akka-http/10.1.10/akka/http/scaladsl/model/HttpRequest.html)格式错误。

本节介绍如何处理每种故障情况，以及在哪些情况下可能发生这些故障。

### 绑定失败

第一种失败类型是服务器无法绑定到给定端口时。例如，当该端口已被另一个应用程序占用时，或者该端口具有特权（即只能由root用户使用）时。在这种情况下，“binding future”将立即失效，我们可以通过侦听Future的完成情况对此做出反应：

```scala
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer

import scala.concurrent.Future

implicit val system = ActorSystem()
implicit val materializer = ActorMaterializer()
// needed for the future foreach in the end
implicit val executionContext = system.dispatcher

// let's say the OS won't allow us to bind to 80.
val (host, port) = ("localhost", 80)
val serverSource = Http().bind(host, port)

val bindingFuture: Future[ServerBinding] = serverSource
  .to(handleConnections) // Sink[Http.IncomingConnection, _]
  .run()

bindingFuture.failed.foreach { ex =>
  log.error(ex, "Failed to bind to {}:{}!", host, port)
}
```

服务器成功绑定到端口后，[Source\[IncomingConnection, \_\]](https://doc.akka.io/api/akka/2.5.23/akka/stream/scaladsl/Source.html)将开始运行并发出新的传入连接。从技术上讲，此源也可以发出故障信号，但是，仅在非常严重的情况下（例如文件描述符或系统可用的内存用完）才能发生故障，这样它就无法接受新的传入连接。在Akka Streams中处理故障是非常直接的，因为故障是通过流发出信号的，从发生故障的阶段开始，一直到下游的最后阶段。

### 连接源故障

在下面的示例中，我们添加了一个自定义[GraphStage](https://doc.akka.io/api/akka/2.5.23/akka/stream/stage/GraphStage.html)，以对流的失败做出反应。我们会向failureMonitor actor发出信号，说明流停止的原因，然后让Actor处理其余的事件 - 也许它将决定重新启动服务器或关闭ActorSystem，但这不再是我们关注的问题。

```scala
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val (host, port) = ("localhost", 8080)
  val serverSource = Http().bind(host, port)

  val failureMonitor: ActorRef = system.actorOf(MyExampleMonitoringActor.props)

  val reactToTopLevelFailures = Flow[IncomingConnection]
    .watchTermination()((_, termination) => termination.failed.foreach {
      cause => failureMonitor ! cause
    })

  serverSource
    .via(reactToTopLevelFailures)
    .to(Sink.foreach { connection =>
      println("Accepted new connection from " + connection.remoteAddress)
    }).run()
}

class MyExampleMonitoringActor extends Actor {
  override def receive: Actor.Receive = {
    //绑定失败打印（这个错误好演示）
    //receive: akka.stream.impl.io.ConnectionSourceStage$$anon$1$$anon$2: Bind failed because of java.net.BindException: Address already in use
    case e: Throwable => println("receive: " + e)
  }
```

### 连接错误

可能发生的第三种类型的故障是在正确建立连接后，然后突然终止，例如，由于客户端中止了底层的TCP连接。

要处理此故障，我们可以使用与上一片段相同的模式，但是将其应用于连接的Flow：

```scala
implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val (host, port) = ("localhost", 8080)
  val serverSource = Http().bind(host, port)

  val reactToConnectionFailure = Flow[HttpRequest]
    .recover[HttpRequest] {
      case ex =>
        // handle the failure somehow
        throw ex
    }

  val httpEcho = Flow[HttpRequest]
    .via(reactToConnectionFailure)
    .map { request =>
      // simple streaming (!) "echo" response:
      HttpResponse(entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, request.entity.dataBytes))
    }

  serverSource
    .runForeach { con =>
      con.handleWith(httpEcho)
    }
```

> 请注意，这是当TCP连接正确关闭时，如果客户端刚刚离开，例如因为网络故障，它将不会被视为此类流故障。它将通过空闲[超时检测](https://doc.akka.io/docs/akka-http/current/common/timeouts.html#timeouts)）。

这些失败可以描述为与基础设施或多或少相关，它们是绑定或连接失败。在大多数情况下，您不需要深入研究这些内容，因为Akka无论如何都会记录此类错误，对于此类问题，这是合理的默认设置。

为了进一步了解如何在实际路由层（即应用程序代码出现的地方）中处理异常，请参阅“[异常处理](https://doc.akka.io/docs/akka-http/current/routing-dsl/exception-handling.html)”，它明确地侧重于说明如何处理路由中引发的异常并将其转换为具有适当错误码的[HttpResponse](https://doc.akka.io/api/akka-http/10.1.10/akka/http/scaladsl/model/HttpResponse.html)和人类可读的故障描述。

[英文原文](https://doc.akka.io/docs/akka-http/current/server-side/low-level-api.html)

