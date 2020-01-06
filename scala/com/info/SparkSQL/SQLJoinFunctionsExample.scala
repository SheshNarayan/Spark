package com.info.SparkSQL

import org.apache.spark.sql.{SparkSession, _}

object SQLJoinFunctionsExample extends App {

  val sparkSession = SparkSession.builder()
    .master("local[*]")
    .appName("TextFileExample")
    .config("spark.sql.warehouse.dir", "D:\\SparkPractice\\SparkData")
    .config("spark.sql.crossJoin.enabled", true) // for joining
    .getOrCreate()

  sparkSession.sparkContext.setLogLevel("WARN")
  val sqlContextObj = sparkSession.sqlContext

  import sqlContextObj.implicits._

  println("============= PARQUET files ========================")
  val parquetFileDF = sparkSession.read.parquet("D:\\SparkPractice\\SparkData\\userdata1.parquet")
  //  parquetFileDF.show(parquetFileDF.count.toInt)
  parquetFileDF.show(5)

  //  println("==== join(DataFrame right) - Cartesian join with another DataFrame. ===")
  //  val joinDF1 = parquetFileDF.filter("id in (10,20)").select("id", "first_name", "last_name")
  //  joinDF1.show()
  //  val joinDF2 = parquetFileDF.filter("id in (10,30,40,50)").select("id", "first_name", "last_name")
  //  joinDF2.show()
  //
  //  joinDF1.join(joinDF2).show()
  //
  //  // Inner join return Only Matched row and common column will not repeate
  //  println("join(DataFrame right, Column joinExprs) - Inner join with another DataFrame, using the given join expression. ==== ")
  //  //join(DataFrame right, Column joinExprs)
  //  // Inner join with another DataFrame, using the given join expression.
  ////  joinDF1.join(joinDF2, new Column("id")).show()
  //  joinDF1.join(joinDF2, "id").show()
  //
  //  println("join(DataFrame right, java.lang.String usingColumn)-Inner equi-join with another DataFrame using the given column.")
  ////  join(DataFrame right, java.lang.String usingColumn)
  ////  Inner equi-join with another DataFrame using the given column.
  //  joinDF1.join(joinDF2, "id").show()
  //
  //  println("join(DataFrame right, scala.collection.Seq<java.lang.String> usingColumns)-Inner equi-join with another DataFrame using the given columns.")
  //  //join(DataFrame right, scala.collection.Seq<java.lang.String> usingColumns)
  //  //Inner equi-join with another DataFrame using the given columns.
  //  joinDF1.join(joinDF2, Seq("id"),"Inner").show()
  //
  //  println("left_outer")
  //  joinDF1.join(joinDF2, Seq("id"),"left_outer").show()
  //
  //  println("right_outer")
  //  joinDF1.join(joinDF2, Seq("id"),"right_outer").show()
  //
  //  println("full_outer")
  //  joinDF1.join(joinDF2, Seq("id"),"full_outer").show()
  //
  //  println("Cross JOIN")
  //  joinDF1.crossJoin(joinDF2).show()

  println()
  //val mapRDD = parquetFileDF.select("first_name","email").map(rec=>rec.getString(1).length+"-"+rec.getString(1))
  //mapRDD.show()
  //
  //  val naDF = parquetFileDF.na
  //  val missingDF = parquetFileDF.na.drop(how = "any")
  //  missingDF.show(missingDF.count.toInt)

  //  parquetFileDF.orderBy("first_name").show(parquetFileDF.count.toInt)

  //  val randomSplitDF = parquetFileDF.randomSplit(Array(0.1, 2.0))
  //  println("randomSplitDF.size:  " + randomSplitDF.size)
  //  println("==========randomSplitDF(0).show(randomSplitDF(0).count.toInt)===============")
  //  randomSplitDF(0).show(randomSplitDF(0).count.toInt)
  //  println("==========randomSplitDF(1).show(randomSplitDF(1).count.toInt)===============")
  //  randomSplitDF(1).show(randomSplitDF(1).count.toInt)
  //
  //  println("=======sample(boolean withReplacement, double fraction)====")
  //  parquetFileDF.sample(true, 0.1).show()
  //  println("=======sample(boolean withReplacement, double fraction, long seed)=======")
  //  parquetFileDF.sample(false, 0.1, 5).show()
  //
  //  println("========== \tschema() ========")
  //  println(parquetFileDF.schema)
  //
  //  println("selectExpr(java.lang.String... exprs) - Selects a set of SQL expressions.")
  //  parquetFileDF.selectExpr("first_name").show(5)
  //  println("====== parquetFileDF.select ==== ")
  //  parquetFileDF.select("first_name").show(5)
  //
  //  println("========= sort/orderBy ============ ")
  //  parquetFileDF.sort($"first_name".desc).show(10)
  //  parquetFileDF.sort("first_name", "last_name").show()
  //
  //  println("========= orderBy/sort ============ ")
  //  parquetFileDF.orderBy("first_name").show(10)
  //  parquetFileDF.orderBy("first_name", "last_name").show()

  /**
   * where(Column condition)
   * Filters rows using the given condition.
   */
  println("=== where(java.lang.String conditionExpr)- Filters rows using the given SQL expression.")
  parquetFileDF.where("first_name like 'A%' and id>20").show()
  /**
   * withColumnRenamed(java.lang.String existingName, java.lang.String newName)
   * Returns a new DataFrame with a column renamed.
   */
  println("withColumnRenamed(java.lang.String existingName, java.lang.String newName) - Returns a new DataFrame with a column renamed.")
  parquetFileDF.where("first_name like 'A%' and id>20")
    .withColumnRenamed("first_name","FIRST_NAME DF").show()

  /**
   * withColumn(java.lang.String colName, Column col)
   * Returns a new DataFrame by adding a column or replacing the existing column that has the same name.
   */
  println(" withColumn(java.lang.String colName, Column col) - Returns a new DataFrame by adding a column or replacing the existing column that has the same name.")
  parquetFileDF.where("first_name like 'A%' and id>20")
    .withColumn("FIRST_NAME DF", new Column("first_name")).show()
}

