package com.info.SparkPairedRDD

import com.info.SparkRDD.MySparkContextObject

object JoinLeftRightFullTransformationExample extends  App{
  val sc = MySparkContextObject.getSparkContextObject

//  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks")
//  val stockSP = stockRDD.map(rec=>(rec.split("\\t")(1),rec.split("\\t")(3).toDouble))

  val dataRDD1 = sc.parallelize(List((100,"Zinta"),(101,"Amit"),(102,"Binit"),(103,"Chandan"),(104,"Deepak"),(105,"Franklin")))
  val dataRDD2 = sc.parallelize(List((101, 8888),(102,7777),(103,6666),(104,5555),(105,4444),(106,9999)),3)
  println("dataRDD1 number of partitions: "+dataRDD1.getNumPartitions)
  println("dataRDD2 number of partitions: "+dataRDD2.getNumPartitions)
  // numPartitions is optional
  println("=== dataRDD1.join(dataRDD2,2) =======")
  val joinRDD = dataRDD1.join(dataRDD2,2)
  joinRDD.foreach(println)
  println("joinRDD number of partitions: "+joinRDD.getNumPartitions)

println("=== dataRDD1.leftOuterJoin(dataRDD2) =======")
  val leftJoinRDD = dataRDD1.leftOuterJoin(dataRDD2)
  leftJoinRDD.foreach(println)
  println("joinRDD1 number of partitions: "+leftJoinRDD.getNumPartitions)

  println("=== dataRDD1.rightOuterJoin(dataRDD2) =======")
  val rightJoinRDD = dataRDD1.rightOuterJoin(dataRDD2)
  rightJoinRDD.foreach(println)
  println("joinRDD1 number of partitions: "+rightJoinRDD.getNumPartitions)

  println("=== dataRDD1.fullOuterJoin(dataRDD2)  =======")
  val fullOuterJoinRDD = dataRDD1.fullOuterJoin(dataRDD2)
  fullOuterJoinRDD.foreach(println)
  println("joinRDD1 number of partitions: "+fullOuterJoinRDD.getNumPartitions)
}
