package com.info.SparkRDD

object IntersectionActionExample extends App {

  val sc = MySparkContextObject.getSparkContextObject
  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks",5)
  println("isEmpty : "+stockRDD.isEmpty())
  val stockRDD1 = sc.textFile("D:\\SparkPractice\\SparkData\\stocks1",5)

  println("========= stockRDD.intersection(stockRDD1) =======")
  val intersectionRDD = stockRDD.intersection(stockRDD1)
  intersectionRDD.foreach(println)

  println("========= intersectionOnPartitionRDD stockRDD.intersection(stockRDD1,1) =======")
  val pd = stockRDD1.glom().collect().deep.mkString(" | ")
  println(pd)
  val intersectionOnPartitionRDD = stockRDD.intersection(stockRDD1,1)
  intersectionOnPartitionRDD.foreach(println)

  // to create empty list
  var list = List[String]()
  val list1 = List.empty[Double]
  val emptyRDD = sc.parallelize(list)
  println("isEmpty : "+emptyRDD.isEmpty())
}
