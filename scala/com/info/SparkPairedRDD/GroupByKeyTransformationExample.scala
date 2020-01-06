package com.info.SparkPairedRDD

import com.info.SparkRDD.MySparkContextObject

/**
 * Important Points
 * Apache spark groupByKey is a transformation operation hence its evaluation is lazy
 * It is a wide operation as it shuffles data from multiple partitions and create another RDD
 * This operation is costly as it doesn’t use combiner local to a partition to reduce the data transfer
 * Not recommended to use when you need to do further aggregation on grouped data
 * groupByKey always results in Hash-Partitioned RDDs
 * This function has three variants
 *
 * groupByKey()
 * groupByKey(numPartition)
 * groupByKey(partitioner)
 * First variant groups the values for each key in the RDD into a single sequence
 * Second variant accepts the arguments for partitions in result RDD
 * And third variant uses partitioner for creating partitions in result RDD
 *
 */
object GroupByKeyTransformationExample extends App {
  val sc = MySparkContextObject.getSparkContextObject

  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks",5)
  val stockSP = stockRDD.map(rec=>(rec.split("\\t")(1),rec.split("\\t")(3).toDouble))
  // println(stockSP)
  val groupRDD = stockSP.groupByKey();
  groupRDD.foreach(println)
  println(groupRDD.count()+"\n No. OF Partition: "+groupRDD.getNumPartitions)

  println("==========stockSP.groupByKey(1) PartitionWise ===========")

  val groupRDDWithPartition = stockSP.groupByKey(2)

  groupRDDWithPartition.foreach(println)
  println(groupRDDWithPartition.getNumPartitions)
  println(groupRDDWithPartition.count())
}
