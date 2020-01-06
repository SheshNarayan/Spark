package com.info.SparkRDD

import scala.runtime.ScalaRunTime._

object KeyByTransformationExample extends App{

  val sc = MySparkContextObject.getSparkContextObject
  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks1",5).setName("MyStockRDD")

  val keyByRDD = stockRDD.keyBy(rec=> rec.length)
  keyByRDD.foreach(println)
  println(stockRDD.name )
  println(stockRDD.partitions.size)


}
