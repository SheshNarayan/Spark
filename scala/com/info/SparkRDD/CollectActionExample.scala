package com.info.SparkRDD

import com.info.SparkRDD.TakeSampleTransformationExample.sc

object CollectActionExample extends App{

  val sc = MySparkContextObject.getSparkContextObject
  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks")
  //println(stockRDD.collect().toList)
  val collectRDD = stockRDD.collect();
  collectRDD.foreach(println)
  println(stockRDD.context)
//
//  val cf = new PartialFunction[String, String] {
//    override def isDefinedAt(x: String): Boolean = !x.isEmpty
//    override def apply( rec: String): String = {
//      val res = rec.split("\\t")
//      if (res(1).equals("CFI"))
//        return rec
//      return ""
//    }
//  }
//
//  val collectRDD1 = stockRDD.collect(cf)
//  //collectRDD1.foreach(println)
//
//
//  def getMatchElement(rec:String):String = {
//    val res = rec.split("\\t")
//    if(res.length == 9 ){
//      return res(1)
//    }
//    return ""
//  }
}
