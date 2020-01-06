package com.info.SparkPairedRDD

import com.info.SparkRDD.MySparkContextObject

object CollectAsMapTransformationExample extends App {
  val sc = MySparkContextObject.getSparkContextObject

  val dataRDD1 = sc.parallelize(List((100,"Zinta"),(100,"Prity Zinta"),(101,"Amit"),(101,"AAAmit"),(102,"Binit"),(103,"Chandan"),(104,"Deepak"),(105,"Franklin")))

  println("=========== dataRDD1.collectAsMap() ===========")
  val collectAsMapRDD = dataRDD1.collectAsMap()
  collectAsMapRDD foreach println

  println("===========collectAsMapRDD.take(3)===========")
  val testRDD = collectAsMapRDD.take(3)
  testRDD.foreach(println)



}
