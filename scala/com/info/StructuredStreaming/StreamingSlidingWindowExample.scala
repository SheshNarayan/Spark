package com.info.StructuredStreaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, sum, to_timestamp, window}
import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructType}

object StreamingSlidingWindowExample extends App{
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

  val aggregateDF = streamingData
    .groupBy(window(
      to_timestamp($"InvoiceDate", "dd-mm-yyyy hh:mm"),
      "1 hour","15 minutes"), col("InvoiceNo")
    )
    .agg(sum("UnitPrice"))

  val query = aggregateDF
    .writeStream
    .outputMode(OutputMode.Complete())
    .format("console")
//    .trigger(Trigger.ProcessingTime("20 seconds"))
    .trigger(Trigger.Once()) // Trigger as Spark Jobs
    .option("truncate", "false")
    .start()

  query.awaitTermination()
}
