package com.info.SparkPairedRDD

import com.info.SparkRDD.MySparkContextObject

object MapValuesFlatMapValuesKeysValuesTransExample extends App {
  val sc = MySparkContextObject.getSparkContextObject

  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks1")
  val stockSP = stockRDD.map(rec => (rec.split("\\t")(1), rec.split("\\t")(3).toDouble))

  //mapValues() - Apply a function to each value of a pair RDD without changing the key.
  val mapValuesResultRDD = stockSP.mapValues(value => value * 2)

  println("====== stockSP.mapValues(value => value * 2) ====== ")
  //mapValuesResultRDD foreach println

  //Apply a function that returns an iterator to each value of a pair RDD, and for each element returned,
  // produce a key/value entry with the old key. Often used for tokenization.
  println("============================")
 // stockSP.foreach(println)
  println("========== flatMapValues === stockSP.flatMapValues(\"*\"+ _ )=======")
  val flatMapValuesRDD = stockSP.flatMapValues("*"+ _ )
  //flatMapValuesRDD foreach println

  println("========  stockSP.keys ===========")
  val keysRDD = stockSP.keys
  keysRDD foreach println

  println("========  stockSP.values ===========")
  val valuesRDD = stockSP.values
  valuesRDD foreach println

  println("========  stockSP.countByValue() ===========")
  val countByValuesRDD = stockSP.countByValue()
  countByValuesRDD foreach println

  println("========  stockSP.countByKey() ===========")
  val countByKeysRDD = stockSP.countByKey()
  countByKeysRDD foreach println

}

