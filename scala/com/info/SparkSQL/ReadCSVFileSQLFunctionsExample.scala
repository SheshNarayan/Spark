package com.info.SparkSQL

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/**
 * groupBy, count(), orderBy
 * agg, as ,min, max, sum, avg
 * collect, col, columns, count, describe
 * distinct, drop, dropDuplicate
 * dtypes
 * explain - display physical and logical plan, based on boolean value
 */
object ReadCSVFileSQLFunctionsExample extends App {

  val sparkSession = SparkSession
    .builder().master("local[6]")
    .appName("Spark SQL Example")
    .config("spark.sql.warehouse.dir", "D:\\SparkPractice\\SparkData")
    .getOrCreate()

  sparkSession.sparkContext.setLogLevel("WARN")
  val sqlContextObj = sparkSession.sqlContext
  val csvFileDF = sqlContextObj.read.format("csv")
    .option("header", true).option("inferSchema", true).option("sep", ",")
    .load("D:\\SparkPractice\\SparkData\\RealEstateTransactions.csv")

  // used for $"city"

  import sqlContextObj.implicits._

  //  csvFileDF.select("street","city","zip","PRICE").show() //Working
  //  sqlContextObj.setConf("spark.sql.retainGroupColumns", "false")
  //  csvFileDF.agg(max($"city"))
  //csvFileDF.select("city").groupBy("city").count().show()

  //  csvFileDF.groupBy("city").count().as("CityCount").show()

  // need to import sqlContextObj.implicits._
  println("  csvFileDF.agg(count(city).as(City Count), max(price),min(sqft),sum(price)).show() ")
  csvFileDF.agg(count($"city").as("City Count"), max($"price"), min($"sqft"), sum($"price")).show()

  println("csvFileDF.groupBy(city).count().orderBy(city.desc).show()")
  csvFileDF.groupBy($"city").count().orderBy($"count".desc).show()

  println("csvFileDF.groupBy(city).agg(sum(price)).show() ")
  csvFileDF.groupBy($"city").agg(sum($"price")).show()

  println("csvFileDF.groupBy(city).agg(avg(price)).show()")
  csvFileDF.groupBy($"city").agg(avg($"price")).show(csvFileDF.count.toInt)

  val rowsArr = csvFileDF.collect()
  println(rowsArr.length)
  rowsArr.take(3).foreach(println)

  val colValue = csvFileDF.col("city")
  println(colValue)

  val columnsNamesArr = csvFileDF.columns
  columnsNamesArr.foreach(println)

  println("====== csvFileDF.describe ====== ")
  csvFileDF.describe("price").show()

  //  val cubeResult = csvFileDF.cube("city","price")
  //  println("cubeResult.sum")
  //  cubeResult.sum("price").show()
  //  println("csvFileDF.cube().count()")
  //  csvFileDF.cube("city","price").count().show()

  csvFileDF.select("city", "beds").distinct().orderBy($"city").show()
  println("csvFileDF.drop(COLUMNS)")
  csvFileDF.drop("longitude").show(5)
  csvFileDF.drop("longitude","latitude").show(5)

  println("dropDuplicates")
  val csvFileDF1 = sqlContextObj.read.format("csv")
    .option("header", true).option("inferSchema", true).option("sep", ",")
    .load("D:\\SparkPractice\\SparkData\\RealEstateTransactions1.csv")

  csvFileDF1.dropDuplicates().show()
  csvFileDF1.dropDuplicates("street","zip").show()
  csvFileDF1.dropDuplicates("city","state").show()

  val dtypeArr = csvFileDF.dtypes
  dtypeArr.foreach(println)

  println("csvFileDF1.explain()")
  println(csvFileDF1.explain(true).toString)
}