package com.info.SparkSQL

import org.apache.spark.sql.SparkSession

/**
 * There are several common join types:
 * INNER , LEFT OUTER , RIGHT OUTER , FULL OUTER and CROSS or CARTESIAN
 */
object SparkDFJoinUsingCaseClass extends App {
  val sparkSession = SparkSession
    .builder().master("local[6]")
    .appName("Spark SQL Example")
    .config("spark.sql.warehouse.dir", "D:\\SparkPractice\\SparkData")
    .getOrCreate()

  sparkSession.sparkContext.setLogLevel("ERROR")

  import sqlContextObj.implicits._
  val sqlContextObj = sparkSession.sqlContext

  case class Pin(pin: Int, area : String)
  case class Block(area : String , block : String)
  case class District(block : String , district : String)

//  val csvPinFileDF = sqlContextObj.read.format("csv")
//    .option("header", true).option("inferSchema", true).option("sep", ",")
//    .load("D:\\SparkPractice\\SparkData\\SparkSQLData\\PinDetails.csv")

  val csvPinFileRDD = sparkSession.sparkContext
    .textFile("D:\\SparkPractice\\SparkData\\SparkSQLData\\PinDetails.csv")

  val csvBlockFileDF = sqlContextObj.read.format("csv")
    .option("header", true).option("inferSchema", true).option("sep", ",")
    .load("D:\\SparkPractice\\SparkData\\SparkSQLData\\BlockDetails.csv")

  val csvDistFileDF = sqlContextObj.read.format("csv")
    .option("header", true).option("inferSchema", true).option("sep", ",")
    .load("D:\\SparkPractice\\SparkData\\SparkSQLData\\DistDetails.csv")

  val pinDF = csvPinFileRDD.map(r => Pin(r(0).toString.toInt,r(1).toString)).toDF() // From RDD To DF
  val blockDF = csvBlockFileDF.map(r => Block(r(0).toString,r(1).toString)).toDF()//From Dataset to DF
  val distDF = csvDistFileDF.map(r => District(r(0).toString,r(1).toString))

  // Select your desired result fields
  val selectExpr= Seq(pinDF("pin"),pinDF("area"),blockDF("block"),distDF("district"))

  pinDF.join(blockDF,pinDF("area")===blockDF("area"),"INNER")
      .join(distDF,blockDF("block")===distDF("block"),"INNER")
      .select(selectExpr:_*)
      .show()
}
