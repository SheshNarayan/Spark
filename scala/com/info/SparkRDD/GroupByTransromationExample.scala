package com.info.SparkRDD

import scala.runtime.ScalaRunTime._

object GroupByTransromationExample extends App{

  val sc = MySparkContextObject.getSparkContextObject
  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks",5)

  println("======== groupBy =========")
  val groupRDD = stockRDD.groupBy(rec => (rec.split("\\t")(1)))
  groupRDD.collect().foreach(println)
//  println(stringOf(groupRDD))

  Thread.sleep(6000)
  println("==== groupByOnPartitionRDD =====")
  val groupByOnPartitionRDD = stockRDD.groupBy(rec => (rec.split("\\t")(1)),1)
  groupByOnPartitionRDD.collect().foreach(println)

//  println("==== groupBy with Odering scala.math.Ordering<K> ord) =====")
//  val groupOrderingRDD = stockRDD.groupBy(rec => (rec.split("\\t")(1)),)
//  groupOrderingRDD.collect().foreach(println)

  println("RDD.ID : "+groupByOnPartitionRDD.id)
  println("RDD.id() : " + stockRDD.id)
}
