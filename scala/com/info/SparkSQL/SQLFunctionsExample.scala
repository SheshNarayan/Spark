package com.info.SparkSQL

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql._

object SQLFunctionsExample extends App {

  val sparkSession = SparkSession.builder()
    .master("local[*]")
    .appName("TextFileExample")
    .config("spark.sql.warehouse.dir", "D:\\SparkPractice\\SparkData")
    .config("spark.sql.crossJoin.enabled",true) // for joining
    .getOrCreate()

  sparkSession.sparkContext.setLogLevel("WARN")
  val sqlContextObj = sparkSession.sqlContext

  import sqlContextObj.implicits._

  println("============= PARQUET files ========================")
  //  df.write.mode('append').parquet("/tmp/output/people.parquet")
  //df.write.mode('overwrite').parquet("/tmp/output/people.parquet")
  val parquetFileDF = sparkSession.read.parquet("D:\\SparkPractice\\SparkData\\userdata1.parquet")
  //  parquetFileDF.show(parquetFileDF.count.toInt)
  parquetFileDF.show(5)

  println(" ====== orderBy ========= parquetFileDF.orderBy(first_name) ==== ")
  parquetFileDF.orderBy("first_name").show()//.show(parquetFileDF.count.toInt)
  println(" ====== filter ========= on id and country ==== ")
  val filterIdDF = parquetFileDF.filter($"id" > 20)
  filterIdDF.show(5)
  val filterCountryDF = parquetFileDF.filter("country = 'Canada' ")
  filterCountryDF.show(5)
  val filterFirstNameDF = parquetFileDF.filter("first_name like 'A%' ")
  filterFirstNameDF.show(5)

  println("===== filter chanining ==== ")
  /**
   * you can use all the where conditions in filter as conditionExpr
   */
  parquetFileDF.filter("country = 'China' and first_name like 'A%'")
    .filter("id > 10").show(5)

  println("============== first - returns first Row ========== \n" + parquetFileDF.first())
  val firstRow = parquetFileDF.first()
  println("No. of Columns : " + firstRow.size)

  println("============== head - returns first Row ========== \n" + parquetFileDF.head())
  val headRow = parquetFileDF.head()
  println("No. of Columns : " + headRow.size)

  println("============== Returns the first n rows - head(N) returns first Row[] ========== ")
  val headRows = parquetFileDF.head(5)
  println("No. of Rows : " + headRows.size)
  headRows.foreach(println)

  println("==== groupBy(java.lang.String col1, java.lang.String... cols) -  Groups the DataFrame using the specified columns, so we can run aggregation on them. ==== ")
  val groupByGD = parquetFileDF.groupBy("Country", "gender").count().orderBy($"country".desc)

  println("==== groupBy(Column... cols) -  Groups the DataFrame using the specified columns, so we can run aggregation on them. ==== ")
  val groupByGD1 = parquetFileDF.groupBy(new Column("Country"), new Column("gender")).count().orderBy($"country".desc)
  groupByGD1.show(5)

  println("================== groupBy(columns(0), columns(1)) =======================")
  //val columns: Array[Column] = Array(new Column("Country"), new Column("gender"))
  val columns: List[Column] = List(new Column("Country"), new Column("gender"))
  val groupByGD2 = parquetFileDF.groupBy(columns(0), columns(1)).count().orderBy($"country".desc)
  groupByGD2.show(5)

  println("============ SUBSET parquetFileDF.limit(20) and parquetFileDF.intersect(subsetDF) ============")
  /**
   * limit(int n)- Returns a new DataFrame by taking the first n rows.
   * intersect(DataFrame other) - Returns a new DataFrame containing rows only in both this frame and another frame.
   */
  val subsetDF = parquetFileDF.filter("id in (10,20,30,40,50,60,70,80,90,100)").limit(20)
//  subsetDF.show()
  parquetFileDF.intersect(subsetDF).show()

  /**
   * isLocal() - Returns true if the collect and take methods can be run locally (without any Spark executors).
   */
  val takeDF = parquetFileDF.collect()
  val takeDF1 = parquetFileDF.take(5)
  val takeDF2 = parquetFileDF.collect().take(5)
  println("parquetFileDF.isLocal : " + parquetFileDF.isLocal)

  println("==== join(DataFrame right) - Cartesian join with another DataFrame. ===")
  val joinDF1 = parquetFileDF.filter("id in (10,20)").select("id", "first_name", "last_name")
  joinDF1.show()
  val joinDF2 = parquetFileDF.filter("id in (10,30,40,50)").select("id", "first_name", "last_name")
  joinDF2.show()

  joinDF1.join(joinDF2).show()

  // Inner join return Only Matched row and common column will not repeate
  println("join(DataFrame right, Column joinExprs) - Inner join with another DataFrame, using the given join expression. ==== ")
  //join(DataFrame right, Column joinExprs)
  // Inner join with another DataFrame, using the given join expression.
  //  joinDF1.join(joinDF2, new Column("id")).show()
  joinDF1.join(joinDF2, "id").show()

  println("join(DataFrame right, java.lang.String usingColumn)-Inner equi-join with another DataFrame using the given column.")
  //  join(DataFrame right, java.lang.String usingColumn)
  //  Inner equi-join with another DataFrame using the given column.
  joinDF1.join(joinDF2, "id").show()

  println("join(DataFrame right, scala.collection.Seq<java.lang.String> usingColumns)-Inner equi-join with another DataFrame using the given columns.")
  //join(DataFrame right, scala.collection.Seq<java.lang.String> usingColumns)
  //Inner equi-join with another DataFrame using the given columns.
  joinDF1.join(joinDF2, Seq("id"),"Inner").show()

  println("left_outer")
  joinDF1.join(joinDF2, Seq("id"),"left_outer").show()

  println("right_outer")
  joinDF1.join(joinDF2, Seq("id"),"right_outer").show()

  println("full_outer")
  joinDF1.join(joinDF2, Seq("id"),"full_outer").show()

  println("Cross JOIN")
  joinDF1.crossJoin(joinDF2).show()

  val randomSplitDF = parquetFileDF.randomSplit(Array(0.1, 2.0))
  println("randomSplitDF.size:  " + randomSplitDF.size)
  println("==========randomSplitDF(0).show(randomSplitDF(0).count.toInt)===============")
  randomSplitDF(0).show(randomSplitDF(0).count.toInt)
  println("==========randomSplitDF(1).show(randomSplitDF(1).count.toInt)===============")
  randomSplitDF(1).show(randomSplitDF(1).count.toInt)

  println("=======sample(boolean withReplacement, double fraction)====")
  parquetFileDF.sample(true, 0.1).show()
  println("=======sample(boolean withReplacement, double fraction, long seed)=======")
  parquetFileDF.sample(false, 0.1, 5).show()

  println("========== \tschema() ========")
  println(parquetFileDF.schema)

  println("selectExpr(java.lang.String... exprs) - Selects a set of SQL expressions.")
  parquetFileDF.selectExpr("first_name").show(5)
  println("====== parquetFileDF.select ==== ")
  parquetFileDF.select("first_name").show(5)

  println("========= sort/orderBy ============ ")
  parquetFileDF.sort($"first_name".desc).show(10)
  parquetFileDF.sort("first_name", "last_name").show()

  println("========= orderBy/sort ============ ")
  parquetFileDF.orderBy("first_name").show(10)
  parquetFileDF.orderBy("first_name", "last_name").show()

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

