package com.info.SparkRDD

object SaveAsTextFileActionExample extends  App{

  val sc = MySparkContextObject.getSparkContextObject

  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks")
  val distinctSymbol = stockRDD.distinct().map(rec=>rec.split("\\t")(1))
  distinctSymbol.foreach(println)
  distinctSymbol.saveAsTextFile("D:\\SparkPractice\\SparkData\\distinctStockSymbol")
//  distinctSymbol.saveAsObjectFile("D:\\SparkPractice\\SparkData\\distinctStockSymbol")
}
