package com.info.SparkPairedRDD

import com.info.SparkRDD.MySparkContextObject

object CountApproxDistinctByKeyTransformationExample extends App {
  val sc = MySparkContextObject.getSparkContextObject

  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks1",5)
  val stockSP = stockRDD.map(rec=>(rec.split("\\t")(1),rec.split("\\t")(3).toDouble))

  println("===== stockSP.countApproxDistinctByKey(0.5) =======")
  val countApproxDistinctByKeyRDD = stockSP.countApproxDistinctByKey(0.2)
  countApproxDistinctByKeyRDD.foreach(println)
}
