package com.info.SparkPairedRDD

import com.info.SparkRDD.MySparkContextObject

/**
 * cogroup(RDD<scala.Tuple2<K,W>> other)
 * For each key k in this or other, return a resulting RDD that contains a tuple
 * with the list of values for that key in this as well as other.
 */
object CogroupTranformationExample extends App {
  val sc = MySparkContextObject.getSparkContextObject

  val dataRDD1 = sc.parallelize(List((100,"Zinta"),(101,"Amit"),(101,"AAAmit"),(102,"Binit"),(103,"Chandan"),(104,"Deepak"),(105,"Franklin")))
  val dataRDD2 = sc.parallelize(List((101, 8888),(101, 88888888),(102,7777),(103,6666),(104,5555),(105,4444),(106,9999)),3)

  println("============ dataRDD1.cogroup(dataRDD2) ========= ")
  val cogroupRDD = dataRDD1.cogroup(dataRDD2)
  cogroupRDD foreach println

  println("============ dataRDD1.fullOuterJoin(dataRDD2) ========= ")
  val fullOuterJoinRDD = dataRDD1.fullOuterJoin(dataRDD2)
  fullOuterJoinRDD foreach println
}
