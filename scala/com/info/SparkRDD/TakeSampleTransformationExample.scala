package com.info.SparkRDD

object TakeSampleTransformationExample extends App{
  val sc = MySparkContextObject.getSparkContextObject
  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks")

  println("takeSample(boolean withReplacement=true, int num=10, long seed=5)")
  val sampleRDD = stockRDD.takeSample(true,10,5)
  sampleRDD.map(println)

  println("takeSample(boolean withReplacement=false, int num=10, long seed=5)")
  val sampleRDD1 = stockRDD.takeSample(false,10,5)
  sampleRDD1.map(println)

}
