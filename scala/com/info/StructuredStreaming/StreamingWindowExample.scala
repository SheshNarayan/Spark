package com.info.StructuredStreaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructType}

object StreamingWindowExample extends App {
  val sparkSession = SparkSession.builder()
    .master("local[*]")
    .appName("Streaming-Window-Sliding-Interval-Example")
    .config("spark.sql.warehouse.dir", "D:\\SparkPractice\\SparkData\\warehouse")
    .getOrCreate()

  sparkSession.sparkContext.setLogLevel("ERROR")

  val sqlContextObj = sparkSession.sqlContext
  import sqlContextObj.implicits._

  val retailDataSchema = new StructType().add("InvoiceNo", IntegerType)
    .add("StockCode", IntegerType).add("Description", StringType, false)
    .add("Quantity", IntegerType).add("InvoiceDate", StringType)
    .add("UnitPrice", DoubleType).add("CustomerID", IntegerType)
    .add("Country", StringType, false)

  val streamingData = sqlContextObj.readStream.schema(retailDataSchema)
    .option("maxFilesPerTrigger", 2)
    .csv("D:\\SparkPractice\\SparkData\\StreamingFiles")

//  val filterData = streamingData.filter("Country='United Kingdom'")
//    .where("Quantity >=30")
 // to_timestamp($"InvoiceDate", "MM/dd/yyyy HH:mm:ss")
//    groupBy is Working
//    .groupBy(window(
//      to_timestamp(col("InvoiceDate"), "dd-mm-yyyy hh:mm"),
//      "4 seconds","2 seconds"), col("InvoiceNo")
//    )
  // You can use $"ColName"
  val aggregateDF = streamingData
    .groupBy(window(
              to_timestamp($"InvoiceDate", "dd-mm-yyyy hh:mm"),
            "4 seconds"), col("InvoiceNo")
            )
    .agg(sum("UnitPrice"))

  val query = aggregateDF
    .writeStream
    .outputMode(OutputMode.Complete())
    .format("console")
    .trigger(Trigger.ProcessingTime("20 seconds"))
    .option("truncate", "false")
    .start()

  query.awaitTermination()
}