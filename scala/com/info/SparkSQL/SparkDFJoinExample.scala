package com.info.SparkSQL

import com.info.SparkSQL.ReadCSVFileSQLFunctionsExample.{sparkSession, sqlContextObj}
import org.apache.spark.sql.SparkSession
import sqlContextObj.implicits._
import com.info.SparkSQL.SparkDFJoinExample.sqlContextObj

object SparkDFJoinExample extends App {
  val sparkSession = SparkSession
    .builder().master("local[6]")
    .appName("Spark SQL Example")
    .config("spark.sql.warehouse.dir", "D:\\SparkPractice\\SparkData")
    .getOrCreate()

  sparkSession.sparkContext.setLogLevel("WARN")
  val sqlContextObj = sparkSession.sqlContext

  case class Pin(pin: Int, area : String)
  case class Block(area : String , block : String)
  case class District(block : String , district : String)

  val csvPinFileDF = sqlContextObj.read.format("csv")
    .option("header", true).option("inferSchema", true).option("sep", ",")
    .load("D:\\SparkPractice\\SparkData\\SparkSQLData\\PinDetails.csv")

  val csvBlockFileDF = sqlContextObj.read.format("csv")
    .option("header", true).option("inferSchema", true).option("sep", ",")
    .load("D:\\SparkPractice\\SparkData\\SparkSQLData\\BlockDetails.csv")

  val csvDistFileDF = sqlContextObj.read.format("csv")
    .option("header", true).option("inferSchema", true).option("sep", ",")
    .load("D:\\SparkPractice\\SparkData\\SparkSQLData\\DistDetails.csv")

  // Select your desired result fields
  val selectExpr= Seq(csvPinFileDF("pin"),csvPinFileDF("area"),csvBlockFileDF("block"),csvDistFileDF("district"))

  csvPinFileDF.join(csvBlockFileDF,csvPinFileDF("area")===csvBlockFileDF("area"),"inner")
    .join(csvDistFileDF,csvBlockFileDF("block")===csvDistFileDF("block"),"inner")
    .select(selectExpr:_*)
//    .select(csvPinFileDF("pin"),csvPinFileDF("area"),csvBlockFileDF("block"))
    . show()

  //  csvPinFileDF.join(csvBlockFileDF,Seq("area"),"inner").show()

//  val pinDS = csvPinFileDF.map(r => Pin(r(0).toString.toInt,r(1).toString))
//  val blockDS = csvBlockFileDF.map(r => Block(r(0).toString,r(1).toString))
//    .registerTempTable("blockTable")
//  val distDS = csvDistFileDF.map(r => District(r(0).toString,r(1).toString))
//    .registerTempTable("distTable")
//  pinDS.registerTempTable("pinDSTable")
//  sqlContextObj.sql("select * from pinDSTable p").show()
//  sqlContextObj.sql("select * from blockTable b").show()
//  sqlContextObj.sql("select * from distTable ").show()
//  sqlContextObj.sql("select p.pin, d.district from pinDSTable p inner join blockTable b inner join distTable d where p.area=b.area and b.block=d.block").show()

}
