package com.info.SparkRDD

object PipeTransformationExample extends App{

  val sc = MySparkContextObject.getSparkContextObject
  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks1",5).setName("MyStockRDD")

  val a = sc.parallelize(1 to 9, 3)
//  a.pipe("head -n 1").collect

}
