package com.info.SparkRDD

import com.info.SparkRDD.FoldActionExample.stockRDD

object CheckpointActionExample extends App{

  val sc = MySparkContextObject.getSparkContextObject
  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks",2)
  sc.setCheckpointDir("D:\\SparkPractice\\SparkData\\checkpointFiles")
  println(stockRDD.getNumPartitions)
  val projRDD = stockRDD.map(record => record.split("\\t")(1))
  val dstRDD = projRDD.distinct()
  dstRDD.checkpoint()

  val dataRDD = dstRDD.map(_.toLowerCase())
//  dstRDD.collect()
  println("===========================")
  println(sc.getCheckpointDir)
  println(dstRDD.isCheckpointed) // false

  dataRDD.collect()
  println("****************************")
  println(dstRDD.isCheckpointed) // true

}
