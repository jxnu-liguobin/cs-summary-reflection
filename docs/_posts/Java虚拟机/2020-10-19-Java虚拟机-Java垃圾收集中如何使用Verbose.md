---
title: Java垃圾收集中如何使用Verbose
categories:
- Java虚拟机
tags: [Java底层]
---

* 目录
{:toc}

# 总览

> Verbose 冗长、详细；输出详细调试信息

在本教程中，我们将研究如何在Java应用程序的垃圾收集中启用Verbose。我们将首先介绍什么是Verbose以及为何有用。

接下来，我们将看几个不同的示例，并了解可用的不同配置选项。此外，我们还将重点介绍如何解读Verbose的输出。

# 垃圾收集中Verbose的简单介绍

**在调整和调试许多问题（尤其是内存问题）时，通常需要打开详细的垃圾收集日志记录。** 实际上，有些人认为，为了严格监控我们的应用程序运行状况，我们应该始终监控JVM的垃圾收集状态。

就像我们将看到的那样，GC日志是一个非常重要的工具，它可以用于揭示对应用程序的堆和GC配置的潜在改进。**对于每次GC的发生，GC日志都会提供有关其结果和持续时间的准确数据。**

随着时间的流逝，对此信息的分析可以帮助我们更好地了解应用程序的行为并帮助我们调整应用程序的性能。此外，**它可以通过指定最佳堆大小，其他JVM选项和替代GC算法来帮助优化GC频率和收集时间。**

## 一个简单的Java程序

我们将使用一个简单的Java程序来演示如何启用和解读我们的GC日志：
```java
public class Application {
 
    private static Map<String, String> stringContainer = new HashMap<>();
 
    public static void main(String[] args) {
        System.out.println("Start of program!");
        String stringWithPrefix = "stringWithPrefix";
 
        // Load Java Heap with 3 M java.lang.String instances
        for (int i = 0; i < 3000000; i++) {
            String newString = stringWithPrefix + i;
            stringContainer.put(newString, newString);
        }
        System.out.println("MAP size: " + stringContainer.size());
 
        // Explicit GC!
        System.gc();
 
        // Remove 2 M out of 3 M
        for (int i = 0; i < 2000000; i++) {
            String newString = stringWithPrefix + i;
            stringContainer.remove(newString);
        }
 
        System.out.println("MAP size: " + stringContainer.size());
        System.out.println("End of program!");
    }
}
```

如上例所示，这个简单的程序将300万个String实例加载到Map对象中。然后，我们使用`System.gc()`显式调用垃圾收集器。

最后，我们从Map中删除了200万个String实例。

在下一节中，我们将看到如何启用GC日志记录。

# 启用“简单”的GC日志记录

让我们开始运行程序，并通过JVM启动参数启用详细GC：
```
-XX:+UseSerialGC -Xms1024m -Xmx1024m -verbose:gc
```

**这里的重要参数是`-verbose:gc`，它以最简单的形式启用垃圾收集信息的记录。** 默认情况下，GC日志写入到stdout，并且应该为每个young generation GC（Young GC）和每个full GC输出一行。

就我们的示例而言，我们通过参数`-XX:+UseSerialGC`指定了串行垃圾收集器，这是最简单的GC实现。

我们还将最小和最大堆大小设置为1024mb，当然，我们可以调整更多的JVM参数。

## 对Verbose输出的基本了解

现在让我们看一下我们的简单程序的输出：
```
Start of program!
[GC (Allocation Failure)  279616K->146232K(1013632K), 0.3318607 secs]
[GC (Allocation Failure)  425848K->295442K(1013632K), 0.4266943 secs]
MAP size: 3000000
[Full GC (System.gc())  434341K->368279K(1013632K), 0.5420611 secs]
[GC (Allocation Failure)  647895K->368280K(1013632K), 0.0075449 secs]
MAP size: 1000000
End of program!
```

在上面的输出中，我们已经可以看到很多有关JVM内部发生的有用信息。

起初，此输出看起来非常艰巨，但现在让我们逐步进行一下。

首先，**我们可以看到发生了四个收集，一个full GC和三个young generation GC。**

## Verbose输出

让我们更详细地分解输出行，以准确了解正在发生的事情：
1. GC或Full GC – **垃圾收集的类型，GC或Full GC可以区分次要垃圾收集或完整垃圾收集**
2. (分配失败)或(System.gc()) – 收集的原因 – **“分配失败”表示在Eden区中没有剩余空间来分配我们的对象**
3. 279616K->146232K - 分别表示在GC之前和之后的已占用堆内存（以箭头分隔）
4. (1013632K) – 堆的当前容量
5. 0.3318607秒 – GC事件的持续时间（以秒为单位）

因此，如果仅看第一行，则279616K->146232K(1013632K)意味着GC将占用的堆内存从279616K减少到146232K。GC时的堆容量为1013632K，GC花费了0.3318607秒。

但是，尽管简单的GC日志记录格式可能很有用，但它提供的信息有限。例如，**我们无法确定GC是否将任何对象从年轻代移到了年老代，或者每次收集之前和之后年轻代的总大小是多少。**

因此，详细的GC记录比简单的记录更为有用。

# 启用“详细”的GC日志记录

**要启用详细的GC日志记录，我们使用参数`-XX:+PrintGCDetails`。** 这将为我们提供有关每个GC的更多详细信息，例如：
* 每次GC之前和之后的年轻代和年老代的大小
* 年轻代和年老代发生GC所需的时间
* 每次GC提升的对象的大小
* 总堆大小的摘要

在下一个示例中，我们将结合使用`-verbose:gc`和这个额外的参数来了解如何在日志中捕获更详细的信息。

请注意，`-XX:+PrintGCDetails`标志在Java 9中已被弃用，以支持新的统一日志记录机制（稍后将对此进行详细介绍）。无论如何，**`-XX:+PrintGCDetails`的新等效项是`-Xlog:gc *`（Java8之前-Xloggc）选项。**

# 解读“详细”的Verbose输出

让我们再次运行示例程序：
```
-XX:+UseSerialGC -Xms1024m -Xmx1024m -verbose:gc -XX:+PrintGCDetails
```

这次的输出更加详细：
```
Start of program!
[GC (Allocation Failure) [DefNew: 279616K->34944K(314560K), 0.3626923 secs] 279616K->146232K(1013632K), 0.3627492 secs] [Times: user=0.33 sys=0.03, real=0.36 secs] 
[GC (Allocation Failure) [DefNew: 314560K->34943K(314560K), 0.4589079 secs] 425848K->295442K(1013632K), 0.4589526 secs] [Times: user=0.41 sys=0.05, real=0.46 secs] 
MAP size: 3000000
[Full GC (System.gc()) [Tenured: 260498K->368281K(699072K), 0.5580183 secs] 434341K->368281K(1013632K), [Metaspace: 2624K->2624K(1056768K)], 0.5580738 secs] [Times: user=0.50 sys=0.06, real=0.56 secs] 
[GC (Allocation Failure) [DefNew: 279616K->0K(314560K), 0.0076722 secs] 647897K->368281K(1013632K), 0.0077169 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
MAP size: 1000000
End of program!
Heap
 def new generation   total 314560K, used 100261K [0x00000000c0000000, 0x00000000d5550000, 0x00000000d5550000)
  eden space 279616K,  35% used [0x00000000c0000000, 0x00000000c61e9370, 0x00000000d1110000)
  from space 34944K,   0% used [0x00000000d3330000, 0x00000000d3330188, 0x00000000d5550000)
  to   space 34944K,   0% used [0x00000000d1110000, 0x00000000d1110000, 0x00000000d3330000)
 tenured generation   total 699072K, used 368281K [0x00000000d5550000, 0x0000000100000000, 0x0000000100000000)
   the space 699072K,  52% used [0x00000000d5550000, 0x00000000ebcf65e0, 0x00000000ebcf6600, 0x0000000100000000)
 Metaspace       used 2637K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 283K, capacity 386K, committed 512K, reserved 1048576K
```

我们应该能够从简单的GC日志中识别所有元素。**但是有几个新条目。**

现在让我们考虑输出中的新条目，这些新条目在下一节中以黑体突出显示：

## 在年轻代中解读Minor GC

我们将从分析Minor GC中的新条目开始：

* [GC (Allocation Failure) **[DefNew: 279616K->34944K(314560K), 0.3626923 secs]** 279616K->146232K(1013632K), 0.3627492 secs] **[Times: user=0.33 sys=0.03, real=0.36 secs]**

和以前一样，我们将这些行分成几部分：
1. DefNew – 使用的垃圾收集器的名称。这个不太明显的名称代表单线程标记复制stop-the-world的垃圾收集器，它是用来收集年轻代的。
2. 279616K->34944K  – 收集前后年轻代的已占用堆内存大小
3. (314560K) – 年轻代的总大小
4. 0.3626923秒 – 持续时间（秒）
5. [Times: user=0.33 sys=0.03, real=0.36 secs] – GC事件的持续时间，以不同类别衡量

现在让我们解释不同的类别：
- user - 垃圾收集器消耗的总CPU时间
- sys - 操作系统调用或等待系统事件所花费的时间
- real - 这是消耗的时间总和，包括其他进程使用的时间片

**由于我们的运行示例使用串行垃圾收集器（始终只使用一个线程），因此real-time等于用户（user）和系统时间（sys）的总和。**

## 解读Full GC

在此倒数第二个示例中，我们看到，对于一个完整收集（Full GC）（由我们的系统调用触发），使用的收集器是Tenured（永久代）。

我们看到的最后一条附加信息是对元空间的相同模式的细分：
```
[Metaspace: 2624K->2624K(1056768K)], 0.5580738 secs]
```

**元空间是Java 8中引入的新内存空间，是本机内存的一个区域。**

## Java堆故障分析

**输出的最后一部分包括堆的细分，包括每个内存部分的内存占用摘要。**

我们可以看到，Eden区占35％的空间，而Tenured占52％的空间。还包括元数据空间和类空间的摘要。

从上面的示例中，**我们现在可以准确了解GC事件期间JVM内部的内存消耗情况。**

# 添加日期和时间信息

没有日期和时间信息的好日志是不完整的。

**当我们需要将GC日志数据与其他来源的数据相关联时，这些额外的信息可能非常有用，或者仅可以帮助您简化搜索。**

我们可以在运行应用程序时添加以下两个参数，以获取日期和时间信息以显示在日志中：
```
-XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps
```

现在，每一行都以写入的绝对日期和时间开头，后跟一个时间戳，以反映自JVM启动以来经过的实时时间（以秒为单位）：
```
2018-12-11T02:55:23.518+0100: 2.601: [GC (Allocation ...
```

请注意，这些调整标志已在Java 9中删除。新的替代方法是：
```
-Xlog:gc*::time
```

# 记录到文件

正如我们已经看到的，默认情况下，GC日志被写入stdout。一个更实际的解决方案是指定一个输出文件。

**我们可以通过使用`-Xloggc:<file>`参数来实现，其中file是输出文件的绝对路径：**
```
-Xloggc:/path/to/file/gc.log
```

与其他调优标志类似，Java 9弃用了-Xloggc标志，以支持新的统一日志记录。更具体地说，现在记录到文件的替代方法是：
```
-Xlog:gc:/path/to/file/gc.log
```

# Java 9: 统一JVM日志记录

从Java 9开始，不赞成使用大多数与GC相关的调整标志，而推荐使用统一日志记录选项`-Xlog:gc`。但是`-verbose:gc`选项仍然可以在Java 9和更高版本中使用。

例如，从Java 9开始，新的统一日志记录系统中的`-verbose:gc`标志等效于：
```
-Xlog:gc
```

这会将所有信息级别的GC日志记录到标准输出中。也可以使用`-Xlog:gc=<level>`语法来更改日志级别。例如，要查看所有调试级别的日志：
```
-Xlog:gc=debug
```

如前所述，我们可以通过`-Xlog:gc=<level>:<output>`语法更改输出目标。默认情况下，输出为stdout，但是我们可以将其更改为stderr甚至文件：
```
-Xlog:gc=debug:file=gc.txt
```

同样，可以使用装饰器在输出中添加更多字段。例如：
```
-Xlog:gc=debug::pid,time,uptime
```

在这里，我们在每个日志语句中打印进程ID，正常运行时间和当前时间戳。

要查看Unified JVM Logging的更多示例，请参阅[JEP 158标准](https://openjdk.java.net/jeps/158)。

# 分析GC日志的工具

使用文本编辑器分析GC日志可能既耗时又繁琐。根据JVM版本和所使用的GC算法，GC日志格式可能会有所不同。

有一个很好的免费图形分析工具，可以分析垃圾收集日志，提供许多有关潜在垃圾收集问题的指标，甚至提供针对这些问题的潜在解决方案。[通用GC日志分析仪](https://gceasy.io/)！


[Verbose Garbage Collection in Java](https://www.baeldung.com/java-verbose-gc)