package com.info.StructuredStreaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import org.apache.spark.sql.types._

object StaticStructureStreamingCSV extends App {

  val spark = SparkSession
    .builder().appName("Static-Structure-Streaming Using CSV")
    .master("local[4]")
    .getOrCreate()
  spark.sparkContext.setLogLevel("ERROR")

  val retailDataSchema = new StructType().add("InvoiceNo", IntegerType)
    .add("StockCode", IntegerType).add("Description", StringType)
    .add("Quantity", IntegerType).add("InvoiceDate", StringType)
    .add("UnitPrice", DoubleType).add("CustomerID", IntegerType)
    .add("Country", StringType)

  // .add("FieldName","DataType i.e. string,integer etc")

  val streamingData = spark.readStream.schema(retailDataSchema)
    .csv("D:\\SparkPractice\\SparkData\\StreamingFiles")

  val filterData = streamingData.filter("Country='Brazil'")

  println("filterData.isStreaming ? : " + filterData.isStreaming)
  val query = filterData
    .writeStream
    .format("console")
    .queryName("FilterByCountry")
    .option("truncate", "false")
    .outputMode("update")
    .start()
    .awaitTermination()

  //.option("nullValue", "0")
  //.format("parquet")
  //    .option("path", "src/main/resources/parquet")
  //    .option("checkpointLocation", "src/main/resources/checkpoint")
  //    .trigger(Trigger.ProcessingTime("10 seconds")
  //    .outputMode(OutputMode.Append())
  //.save("D:\\SparkPractice\\SparkData")

  // or .outputMode(OutputMode.Complete())


  //val Test_Output =spark.sql("select A.Col1, A.Col2, B.Col2, C.Col2, D.Col2 from A, B, C, D where A.primaryKey = B.primaryKey and B.primaryKey = C.primaryKey and C.primaryKey = D.primaryKey and D.primaryKey = A.primaryKey")

  //  val Test_Output_File = Test_Output.coalesce(1).write
  //    .format("com.databricks.spark.csv").option("header", "true")
  //    .option("nullValue", "0").save("D:/Test_Output_File")
  //query.awaitTermination()

}
