package com.info.SparkPairedRDD

import com.info.SparkRDD.MySparkContextObject

/**
 * Sort the RDD by key, so that each partition contains a sorted range of the elements.
 * Calling collect or save on the resulting RDD will return or output an ordered list of records
 * (in the save case, they will be written to multiple part-X files in the filesystem, in order of the keys).
 */
object SortByKeyTranformationExample extends App {
  val sc = MySparkContextObject.getSparkContextObject

  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks")
  val stockSP = stockRDD.map(rec=>(rec.split("\\t")(1),rec.split("\\t")(3).toDouble))

  println("==== stockSP.sortByKey()==== Ascending Order ====")
  val sortByKeyRDD = stockSP.sortByKey()
  val sortedDataRDD = sortByKeyRDD.collect()
  sortedDataRDD.foreach(println)
  println("Number of Partition: "+sortByKeyRDD.getNumPartitions+"\t Number of Record: "+sortByKeyRDD.count())

  println("==== stockSP.sortByKey(false,2) ==== Descending Order ====")
  val sortByKeyRDD1 = stockSP.sortByKey(false,2)
  val sortedDataRDD1 = sortByKeyRDD1.collect()
  sortedDataRDD1.foreach(println)
  println("Number of Partition: "+sortByKeyRDD1.getNumPartitions+"\t Number of Record: "+sortByKeyRDD1.count())
}
