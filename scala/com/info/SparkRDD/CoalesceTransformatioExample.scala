package com.info.SparkRDD

object CoalesceTransformatioExample extends App{
  /**
   * coalesce(int numPartitions,
   *          boolean shuffle,
   *          scala.Option<PartitionCoalescer> partitionCoalescer,
   *          scala.math.Ordering<T> ord)
   * Return a new RDD that is reduced into numPartitions partitions.
   */

  val sc = MySparkContextObject.getSparkContextObject
  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks",10)
  println(stockRDD.getNumPartitions)
  val coalesceRDD = stockRDD.coalesce(6)
  println(coalesceRDD.getNumPartitions)

  val coalesceRDD1 = stockRDD.coalesce(6,false)
  println(coalesceRDD1.getNumPartitions)


}
