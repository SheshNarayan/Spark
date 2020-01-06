package com.info.SparkSQL

import org.apache.spark.sql.SparkSession


object ReadTextFileSQLFunctionsExample extends App {

  val sparkSession = SparkSession.builder()
    .master("local[*]")
    .appName("TextFileExample")
    .config("spark.sql.warehouse.dir", "D:\\SparkPractice\\SparkData")
    .getOrCreate()

  sparkSession.sparkContext.setLogLevel("WARN")
  val sqlContextObj = sparkSession.sqlContext

  import sqlContextObj.implicits._

  //  val textFileDF = sparkSession.read
  //    .option("header", true).option("inferSchema", true).option("delimiter","\t")
  //    .text("D:\\SparkPractice\\SparkData\\stocks2.txt").toDF()

  //  val txtDF = sparkSession.read.format("csv")
  //    .option("header", false).option("inferSchema", true).option("sep", "\t")
  //    .load("D:\\SparkPractice\\SparkData\\stocks2.txt")
  //    txtDF.show()

  //  val stockRDD = sparkSession.sparkContext
  //    .textFile("D:\\SparkPractice\\SparkData\\stocks2.txt")
  //    .map(rec => rec.split("\t")).map(attr => StockRecord(attr(1), attr(3).toDouble, attr(7).toLong))

  println("============= TEXT files ========================")
  val stockDF = sparkSession.sparkContext.textFile("D:\\SparkPractice\\SparkData\\stocks2.txt")
    .map(rec => rec.split("\t")).map(attr => StockRecord(attr(1), attr(3).toDouble, attr(7).toLong)).toDF()

  // stockDF.show()
  case class StockRecord(stock: String, price: Double, totalPrice: Long)

  val stockDF1 = sparkSession.sparkContext.textFile("D:\\SparkPractice\\SparkData\\stocks2.txt")
    .map(rec => rec.split("\t")).map(attr => (attr(1), attr(3).toDouble, attr(7).toLong)).toDF("Stock", "Price", "TotalPrice")
  stockDF1.show(5)


  println("============= PARQUET files ========================")
  //  df.write.mode('append').parquet("/tmp/output/people.parquet")
  //df.write.mode('overwrite').parquet("/tmp/output/people.parquet")
  //stockDF1.write.parquet("D:\\SparkPractice\\SparkData\\stocks.parquet")
  val parquetFileDF = sparkSession.read.parquet("D:\\SparkPractice\\SparkData\\userdata1.parquet")
  parquetFileDF.show(5)

  println("============= ORC Files ========================")
  val cols = Seq("registration_dttm", "id","first_name", "last_name", "email", "gender", "ip_address", "cc", "country", "birthdate", "salary", "title","comments")
  val orcFileDF = sparkSession.read.orc("D:\\SparkPractice\\SparkData\\userdata1_orc").toDF(cols :_*)
  orcFileDF.show(7)

  println("============= Sequence Files ========================")
  //val sequenceFileDF = sparkSession.read.format("sequencefile").load("D:\\SparkPractice\\SparkData\\plain_seq\\seq_01.seq")
  //val sequenceFileDF = sparkSession.sparkContext.sequenceFile("D:\\SparkPractice\\SparkData\\plain_seq\\seq_01.seq")
  //sequenceFileDF.show()
}

