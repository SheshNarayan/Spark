package com.info.SparkRDD

import org.apache.spark.{SparkConf, SparkContext}

object MySparkContextObject extends App{
   def getSparkContextObject :SparkContext = {
     val conf = new SparkConf().setAppName("RDD Example").setMaster("local[4]")
     val sparkContext = new SparkContext(conf)
     sparkContext.setLogLevel("ERROR")
     println(conf + "\t" + sparkContext)
     return sparkContext
   }
}
