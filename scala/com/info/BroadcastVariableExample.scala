package com.info

import org.apache.spark.{SparkConf, SparkContext}

object BroadcastVariableExample extends App {
  val conf = new SparkConf().setAppName("RDD Example").setMaster("local[4]")
  val sc = new SparkContext(conf)
  sc.setLogLevel("ERROR")
  println("====== Without Boradcast variable ============")
  val rdd1 = sc.parallelize(List(1, 2, 3))
  val localVar = 2;

  println(" === rdd1.map(x => x + localVar) === ")
  val rdd2 = rdd1.map(x => x + localVar)
  rdd2 foreach (println)

  println(" === rdd1.map(x => x * localVar) === ")
  val rdd3 = rdd1.map(x => x * localVar)
  rdd3 foreach (println)

  println("====== With Boradcast variable ============")
  val broadcastVariable = sc.broadcast(9)

  println(" === rdd1.map(x => x + broadcastVariable.value) === ")
  val rdd4 = rdd1.map(x => x + broadcastVariable.value)
  rdd2 foreach (println)

  println(" === rdd1.map(x => x * broadcastVariable.value) === ")
  val rdd5 = rdd1.map(x => x * broadcastVariable.value)
  rdd3 foreach (println)

  println("broadcastVariable Id: "+broadcastVariable.id)
}
