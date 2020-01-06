package com.info.SparkRDD

object SortByTransformationExample extends App {

  val sc = MySparkContextObject.getSparkContextObject

  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks")
    val distinctSymbol = stockRDD.map(rec => (rec.split("\\t")(1), (rec.split("\\t")(2)))).distinct()
//  val distinctSymbol = stockRDD.map(rec => rec.split("\\t")(1)).distinct() // on single value
  /**
   * Once we have sorted our data,any subsequent call on the sorted data to collect() or save()
   * will result in ordered data.
   * */
  val sortedRDD = distinctSymbol.sortBy(x => x, true).collect()
  sortedRDD.foreach(println)
  //  distinctSymbol.saveAsObjectFile("D:\\SparkPractice\\SparkData\\distinctStockSymbol")
}
