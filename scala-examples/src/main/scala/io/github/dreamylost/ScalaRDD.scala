package io.github.dreamylost

object ScalaRDD extends App {

  /**
    * 参考https://blog.csdn.net/qq_21439395/article/details/81023400
    */
  val d1 = Array(("bj", 28.1), ("sh", 28.7), ("gz", 32.0), ("sz", 33.1))
  val d2 = Array(("bj", 27.3), ("sh", 30.1), ("gz", 33.3))
  val d3 = Array(("bj", 28.2), ("sh", 29.1), ("gz", 32.0), ("sz", 30.5))

  /**
    * 思路：
    *
   * 1，对数据进行合并
    *
   * 2，按照城市进行分组
    *
   * 3，分组后进行聚合统计
    */

  //1，对数据进行合并的实现 union ++
  val data: Array[(String, Double)] = d1 union d2 ++ d3
  println("=========================union===========================")
  data.foreach(println)

  //2，按照城市进行分组
  val grouped: Map[String, Array[(String, Double)]] = data.groupBy(_._1)
  println("=========================groupBy===========================")
  grouped.foreach {
    case (city, data) => {
      println(city)
      data.foreach {
        case (x, y) => println(x, y)
      }
    }
  }

  println("=========================mapValues===========================")
  //1.利用mapvalues算子，分别统计总的温度，以及月份的次数，然后求得平均温度
  grouped
    .mapValues(t => {
      val totalSum = t.map(_._2).sum
      val len = t.length
      totalSum / len
    })
    .foreach(println)

  println("=========================foldLeft===========================")
  //2.利用foldLeft来实现，需要注意的是，因为初始值类型是Double，而元素类型是元组类型，所以这里不能用fold实现
  grouped
    .mapValues(t => {
      val sum = t.foldLeft(0d)(_ + _._2)
      sum / t.length
    })
    .foreach(println)

  println("=========================reduceLeft===========================")
  //3.利用reduce或者reduceLeft 实现。因为reduce和reduceLeft的特性，这里的元素是元组类型，要求返回值类型也得是元组类型。
  //所以，需要组装成元组，再取第二个元素。即为温度总值。然后再除以长度，得到结果值。
  grouped
    .mapValues(t => {
      t.reduceLeft((a, b) => ("", a._2 + b._2))._2 / t.length
    })
    .foreach(println)

  println("=========================aggregate===========================")
  //4.利用aggregate实现。同样需要传递一个Double类型的初始值，然后进行统计计算。
  grouped
    .mapValues(t => {
      t.aggregate(0d)(_ + _._2, _ + _) / t.length
    })
    .foreach(println)

  //1.map(Func)
  //2.flatMap(fun) 将map的结果中以list展开
  //3.mapPartitions(func)，作用类似于map，map作用于每个分区的每个元素，而mapPartitions作用于整个分区
  //4.sample(withReplacement,fraction,seed) 抽样，withReplacement设置是否放回，true是放回，false是不放回，fraction是样本比例，seed随机种子值
  //5.union(anotherDataset): 合并RDD，不会去重相同元素
  //6.intersection(otherDataset):返回两个RDD的交集
  //7.cartesian(anotherDataset) 对两个RDD进行笛卡尔乘积
}
