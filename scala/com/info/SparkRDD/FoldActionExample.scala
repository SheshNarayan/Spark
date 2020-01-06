package com.info.SparkRDD

object FoldActionExample extends App {

  val sc = MySparkContextObject.getSparkContextObject
  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks1",2)
  println(stockRDD.getNumPartitions)
  val stockRecordRDD=stockRDD.map(record => record.split("\\t")(8).toDouble)
  val result = stockRecordRDD.fold(0)((a,b)=>a+b)
  println(result)

  val result1 = stockRDD.filter(record => record.split("\\t")(1) == "CMP")
                          .map(record => record.split("\\t")(8).toDouble)
                          .fold(0.0)(_ + _)
  println(result1)

  val result2 = stockRDD.filter(record => record.split("\\t")(1) == "CMP")
    .map(record => record.split("\\t")(8).toDouble)
    .fold(0.0)(_ + _)

  println(result2)

  println("===========================")
  stockRDD.foreachPartition(rec => rec.foreach(println))
}
