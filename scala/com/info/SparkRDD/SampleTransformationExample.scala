package com.info.SparkRDD

object SampleTransformationExample extends App{

  val sc = MySparkContextObject.getSparkContextObject
  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks")

  println("Sample(boolean withReplacement=true, int num=10, long seed=5)")
  val sampleRDD = stockRDD.sample(true,0.1,15)//.take(10)
//  sampleRDD.map(println)
  println(sampleRDD.count())

  println("Sample(boolean withReplacement=false, int num=10, long seed=5)")
  val sampleRDD1 = stockRDD.sample(false,0.1,5)
                           .take(10)
  sampleRDD1.map(println)

}
