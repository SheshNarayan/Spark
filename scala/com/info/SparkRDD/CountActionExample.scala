package com.info.SparkRDD

object CountActionExample extends App {

  val sc = MySparkContextObject.getSparkContextObject
  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks",6)

  println(stockRDD.count())

  val countApp = stockRDD.countApprox(500)
  println("stockRDD.countApprox(500)  :   "+countApp)
  val countApp1 = stockRDD.countApprox(800, 0.9)
  println("stockRDD.countApprox(800, 0.9):  "+countApp1)

  val countAppDist = stockRDD.countApproxDistinct(0.05)
  println("stockRDD.countApproxDistinct(0.05):  "+countAppDist)

  val countAppDist1 = stockRDD.countApproxDistinct(10,0)
  println("stockRDD.countApproxDistinct(0.05):  "+countAppDist1)

}
