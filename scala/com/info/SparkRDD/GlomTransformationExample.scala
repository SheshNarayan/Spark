package com.info.SparkRDD
import scala.runtime.ScalaRunTime._

object GlomTransformationExample extends App{

  val sc = MySparkContextObject.getSparkContextObject
  val dataList = List(50.0,40.0,60.0,70.0,80,20, 90)
  val dataRDD1 = sc.makeRDD(dataList,3)
  println("============ TO get glom() Data user define ===================")
  val glomRDD = dataRDD1.glom().collect()
  println(glomRDD.deep.mkString("\n"))
  println("===============================")
  val str = stringOf(glomRDD)
  println(str)
  println("===============================")
  val maxValueRDD = dataRDD1.glom().map((value:Array[Double]) => value.max)//.reduce( (r,a) => r max a)
  println("Maximum Values From Each Partitions")
  maxValueRDD.foreach(println)
  val maxValue = maxValueRDD.reduce((r,a) => r max a) // _ max _
  println("Max value : "+maxValue)

  println("================== STOCK DATA ===================== ")
  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks",5)
  //val dataRDD = stockRDD.map(rec => rec.split("\\t")(8).toDouble)
  val dataRDD = stockRDD.map(rec => rec.split("\\t")(1))
  println("============ Partition wise Data ===================")
//  val glomStockRDD = dataRDD.glom()
//  val distStockRDD = glomStockRDD.collect()
//  val glomStr = stringOf(distStockRDD)
//  println(glomStr)
//  println("Number Of Partitions in stockRDD :"+stockRDD.getNumPartitions)
//
//  val maxPriceByPartation = glomStockRDD.map(rec => rec.max)
//  maxPriceByPartation.foreach(println)
//  val maxStockPrice = maxPriceByPartation.reduce((a,b)=>a max b)
//  println("Maximum Stock Price : "+maxStockPrice)
//
//  println(" =========== Total Stock Price =========== ")
//  val totalPriceByPartation = glomStockRDD.map(rec => rec.fold(0.0) (_ + _))
//  totalPriceByPartation.foreach(println)
//  val totalPrice = totalPriceByPartation.reduce((a,b)=>a + b)
//  println("Total Stock Price : "+totalPrice)

    val glomStockRDD = dataRDD.glom()
    val distStockRDD = glomStockRDD.collect()
    val glomStr = stringOf(distStockRDD)
    println(glomStr)
    println("Number Of Partitions in stockRDD :"+stockRDD.getNumPartitions)
    println("==== Partition wise distinct stock symbol ========== ")
    val distinctPartationStockSymbol = glomStockRDD.map(rec => rec.distinct).collect()
    println(stringOf(distinctPartationStockSymbol))

    println("==== Distinct stock symbol ========== ")
//  val distinctStockSymbol =  .map(record => record)
//  println(stringOf(distinctStockSymbol))
}



