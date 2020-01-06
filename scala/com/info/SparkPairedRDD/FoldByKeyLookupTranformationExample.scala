package com.info.SparkPairedRDD

import com.info.SparkRDD.MySparkContextObject

object FoldByKeyLookupTranformationExample extends App {
  val sc = MySparkContextObject.getSparkContextObject

  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks1")
  val stockSP = stockRDD.map(rec => (rec.split("\\t")(1), rec.split("\\t")(3).toDouble))

  println("stockSP.getNumPartitions : "+stockSP.getNumPartitions)
  val foldByKeyRDD = stockSP.foldByKey(0)((a,b) => a+b)
  foldByKeyRDD foreach(println)

  val lookupRDD = stockSP.lookup("CMA")
  lookupRDD foreach(println)
}
