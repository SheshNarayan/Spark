package com.info.SparkRDD

object CountByValueActionExample  extends App {

  val sc = MySparkContextObject.getSparkContextObject
  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks",6)

  val stockSymbol = stockRDD.map(rec => rec.split("\\t")(1))
  stockSymbol.countByValue().foreach(println)

  /**
   * countByValueApprox
   * Marked as experimental feature! Experimental features are currently not covered by this document!
   * Listing Variants
   * def countByValueApprox(timeout: Long, confidence: Double = 0.95): PartialResult[Map[T, BoundedDouble]]
   */
  val result = stockSymbol.countByValueApprox(200, 0.1)
  println(result)
}
