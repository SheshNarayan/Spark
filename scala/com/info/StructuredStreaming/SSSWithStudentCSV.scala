package com.info.StructuredStreaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType, StructType}

object SSSWithStudentCSV extends App {
  val spark = SparkSession
    .builder.appName("Structured Streaming to read data from Student CSV")
    .master("local[4]").getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")
  val studentSchema = new StructType().add("Name", "string")
    .add("Age", "integer")
    .add("Marks", DoubleType)

  val studentDF = spark
    .readStream
    .schema(studentSchema) // Specify schema of the csv files
    .csv("D:\\SparkPractice\\SparkData\\StreamingFiles")

  val names = studentDF.writeStream.outputMode("update")
    .format("console")
    .option("header", "true")
    .start()
    .awaitTermination()

  //  csvDF.printSchema();
  //
  //  csvDF.select("name").show()

  //  val studDF = spark.read.format("csv").load("D:\\SparkPractice\\SparkData\\Student.csv")
  //  val query = studDF.writeStream
  //    .outputMode("complete")
  //    .format("console")
  //    .start()
  //.queryName("aggregates")    // this query name will be the table name
  //  query.awaitTermination()


}
