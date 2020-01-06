package com.info.StructuredStreaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType

object SparkStructuredStreamingWithCSV extends App {
  val spark = SparkSession
    .builder.appName("StructuredNetworkWordCount")
    .master("local[4]").getOrCreate()

  val userSchema = new StructType().add("name", "string")
    .add("age", "integer")

  val csvDF = spark
    .readStream
    .schema(userSchema) // Specify schema of the csv files
    .csv("D:\\SparkPractice\\SparkData\\StreamingFiles")

  val names = csvDF.groupBy("name")
    .count()
    .writeStream.outputMode("update")
    .format("console")
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
  //
  //  query.awaitTermination()


}
