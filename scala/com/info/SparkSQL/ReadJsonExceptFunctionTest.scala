package com.info.SparkSQL

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

/**
 * show(), printSchema(), distinct(), select()
 * option - header, inferSchema
 * read JSON file- SingleLine, MultiLine
 * printSchema,
 */
object ReadJsonExceptFunctionTest extends App {

  //  val conf = new SparkConf().setAppName("RDD Example").setMaster("local[4]")
  //  val sparkContext = new SparkContext(conf)
  //  sparkContext.setLogLevel("WARN")

  val sparkSession = SparkSession
    .builder().master("local[6]")
    .appName("Spark SQL Example")
    .config("spark.sql.warehouse.dir", "D:\\SparkPractice\\SparkData")
    .getOrCreate()

  sparkSession.sparkContext.setLogLevel("WARN")

  println("Spark Session : " + sparkSession)
  val sqlContextObj = sparkSession.sqlContext
  println("sparkSession.sqlContext : " + sqlContextObj)
  //  val stockDF = sparkSession.read.text("D:\\SparkPractice\\SparkData\\Stocks1")
  //  stockDF.show()

  // You can read or load the data file using sqlContextObj Object as well as sparkSession Object
  val studentSingleLineDF = sqlContextObj.read.option("header", true).option("inferSchema",true)
    .json("D:\\SparkPractice\\SparkData\\employees_singleLine.json")
  studentSingleLineDF.show()
  studentSingleLineDF.printSchema()

  studentSingleLineDF.select("deptno","empno","ename").show()
  studentSingleLineDF.select("deptno").distinct().show()
//   /* For Multiline json file. read whole file with sc the get the values */
  val studentMultiLineDF = sparkSession.read.json(sparkSession.sparkContext.wholeTextFiles("D:\\SparkPractice\\SparkData\\employees_multiLine.json").values)
  studentMultiLineDF.show()

  /**
   * dataFrame1.except(dataFrame2)
   * will return a new DataFrame containing rows in dataFrame1 but not in dataframe2.
   */
  println("=========== studentSingleLineDF.except(studentMultiLineDF).show() =============")
  studentSingleLineDF.except(studentMultiLineDF).show()

  println("=========== studentMultiLineDF.except(studentSingleLineDF) =============")
  studentMultiLineDF.except(studentSingleLineDF).show()

  println("=========== studentSingleLineDF.exceptAll(studentMultiLineDF).show() =============")
  studentSingleLineDF.exceptAll(studentMultiLineDF).show()

  println("=========== studentMultiLineDF.exceptAll(studentSingleLineDF).show() =============")
  studentMultiLineDF.exceptAll(studentSingleLineDF).show()


}
