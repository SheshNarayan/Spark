package com.info.SparkRDD

object RepartitionTransformationExample extends App{
  val sc = MySparkContextObject.getSparkContextObject
  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks",3)
  println(stockRDD.getNumPartitions)


  val repartitionStockRDD = stockRDD.repartition(5)

  println(repartitionStockRDD.getNumPartitions)

}
