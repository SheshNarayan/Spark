package com.info.SparkPairedRDD

import com.info.SparkRDD.MySparkContextObject

object SubtractByKeyTransformationExample extends App {
  val sc = MySparkContextObject.getSparkContextObject

  val dataRDD1 = sc.parallelize(List((100,"Zinta"),(100,"Prity Zinta"),(101,"Amit"),(101,"AAAmit"),(102,"Binit"),(103,"Chandan"),(104,"Deepak"),(105,"Franklin")))
  val dataRDD2 = sc.parallelize(List((101, 8888),(101, 88888888),(102,7777),(103,6666),(104,5555),(105,4444),(106,9999),(106,9999999)),3)

  println("===== dataRDD1.subtractByKey(dataRDD2) ======")
  val subtractByKeyRDD = dataRDD1.subtractByKey(dataRDD2)
  subtractByKeyRDD.foreach(println)

  println("===== dataRDD2.subtractByKey(dataRDD1) ======")
  val subtractByKeyRDD1 = dataRDD2.subtractByKey(dataRDD1)
  subtractByKeyRDD1.foreach(println)

}
