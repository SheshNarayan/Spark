package com.info.SparkRDD



object TakeActionExample extends App {
  val sc = MySparkContextObject.getSparkContextObject

  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks",5)
  stockRDD.take(1).foreach(println)
  println("==============================")
  stockRDD.takeOrdered(10).foreach(println)
  println("==============================")
  val sampleRDD1 = stockRDD.takeSample(true,10,5)
  sampleRDD1.map(println)
  println("==========toLocalIterator()===================")
  val stockItr = stockRDD.toLocalIterator
  stockItr.foreach(println)
//  while(stockItr.hasNext){
//    println(stockItr.next())
//  }
  println("=============top(int num, scala.math.Ordering<T> ord)=============")
  val toprecord = stockRDD.top(10)
  toprecord.foreach(println)
}
