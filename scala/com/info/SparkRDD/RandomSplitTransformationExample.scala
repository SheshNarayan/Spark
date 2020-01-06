package com.info.SparkRDD

object RandomSplitTransformationExample extends App {

  /**
   * Randomly splits an RDD into multiple smaller RDDs according to a weights Array which specifies the
   * percentage of the total data elements that is assigned to each smaller RDD.
   * Note the actual size of each smaller RDD is only approximately equal to the percentages
   * specified by the weights Array.
   * The second example below shows the number of items in each smaller RDD does not exactly
   * match the weights Array.
   * A random optional seed can be specified.
   * This function is useful for spliting data into a training set and a testing set for machine learning.
   *
   * Listing Variants
   * def randomSplit(weights: Array[Double], seed: Long = Utils.random.nextLong): Array[RDD[T]]
   */

  val sc = MySparkContextObject.getSparkContextObject
  val y = sc.parallelize(1 to 10)
  val splits = y.randomSplit(Array(0.4, 0.3, 0.3), seed = 11L)
  println("===========" + splits.size)
  val split0 = splits(0)
  val split1 = splits(1)
  val split2 = splits(2)

  println("====split 0 =========")
  split0.foreach(println)
  println("====split 1 =========")
  split1.foreach(println)
  println("====split 2=========")
  split2.foreach(println)

}
