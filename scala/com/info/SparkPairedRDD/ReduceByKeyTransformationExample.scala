package com.info.SparkPairedRDD

import com.info.SparkRDD.MySparkContextObject

/**
 * Important points to note are,
 *
 * reduceByKey is a transformation operation in Spark hence it is lazily evaluated
 * It is a wide operation as it shuffles data from multiple partitions and creates another RDD
 * Before sending data across the partitions, it also merges the data locally using the same associative function for optimized data shuffling
 * It can only be used with RDDs which contains key and value pairs kind of elements
 * It accepts a Commutative and Associative function as an argument
 * The parameter function should have two arguments of the same data type
 * The return type of the function also must be same as argument types
 * This function has three variants
 *
 * reduceByKey(function)
 * reduceByKey(function, [numPartition])
 * reduceByKey(partitioner, function)
 * Variants 1 will generate hash-partitioned output with existing partitioner
 * Variants 2 will generate hash-partitioned output with number of partitions given by numPartition
 * Variants 3 will generate output using Partitioner object referenced by partitioner
 */
object ReduceByKeyTransformationExample extends App {
  val sc = MySparkContextObject.getSparkContextObject

  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks",5)
  val stockSP = stockRDD.map(rec=>(rec.split("\\t")(1),rec.split("\\t")(3).toDouble))

  val redcedRDD = stockSP.reduceByKey(myInitialPriceAggregator)
  redcedRDD.foreach(println)
  println("Total Number of Record: "+redcedRDD.count()+"\t Number of Partition : "+redcedRDD.getNumPartitions)

  val redcedWithPartitionRDD = stockSP.reduceByKey((acc,data)=>myInitialPriceAggregator(acc,data),2)
  redcedRDD.foreach(println)
  println("Total Number of Record: "+redcedWithPartitionRDD.count()+"\t Number of Partition : "+redcedWithPartitionRDD.getNumPartitions)

  def myInitialPriceAggregator(acc: Double, data: Double): Double = {
    return acc+data
  }


}
