package com.info.SparkRDD

object SubstractTransformationExample extends App {

  val sc = MySparkContextObject.getSparkContextObject

  val rdd1 = sc.parallelize(1 to 10, 2)
  val rdd2 = sc.parallelize(1 to 10 by 2, 2)
  println("==========RDD1==============")
  rdd1.foreach(println)
  println("\n==========RDD2==============")
  rdd2.foreach(println)
  println("\n=========subtractResult===============")
  val subtractResult = rdd1.subtract(rdd2,1)
  subtractResult.foreach(println)
}
