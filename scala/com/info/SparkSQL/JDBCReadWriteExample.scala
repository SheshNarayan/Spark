package com.info.SparkSQL

import org.apache.spark.sql.SparkSession

object JDBCReadWriteExample extends App {

  // Creating SparkSession object with all the options
  val sparkSession = SparkSession.builder()
    .master("local[*]")
    .appName("TextFileExample")
    .config("spark.sql.warehouse.dir", "D:\\SparkPractice\\SparkData")
    .config("spark.sql.crossJoin.enabled", true) // for joining
    .getOrCreate()

  sparkSession.sparkContext.setLogLevel("WARN")
  val sparkSQLContextObj = sparkSession.sqlContext

  // Download the mysql-connector-java-5.1.25 and add it in  SPARK_HOME/bin
  // OR add the mysql dependency to sbt i.e. libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.25"
  // For establish the connection to MySql provide the Connection details
  // i.e. Driver, url, username, password and table name as below.

  // load method will read the data from table and store in Dataframe i.e. mySqlDF
  val mySqlDF = sparkSQLContextObj.read.format("jdbc")
    .option("url", "jdbc:mysql://localhost:3306/shesh")
    .option("driver", "com.mysql.jdbc.Driver")
    .option("dbtable", "employee")
    .option("user", "root")
    .option("password", "root").load()

  println("==== mySqlDF.show() === Display the Dable data")
  mySqlDF.show()

  //Registering the table with temporary name
  val tempTable = mySqlDF.registerTempTable("emp")

  println(" ===== mySqlDF.sqlContext.sql(\"select * from emp where eid > 105\").collect().foreach(println) =====")
  mySqlDF.sqlContext.sql("select * from emp where eid > 105").collect().foreach(println)

  println(" ===== mySqlDF.sqlContext.sql(\"select * from emp where eid > 105\").show() ====== ")
  mySqlDF.sqlContext.sql("select * from emp where eid > 105").show()

  println(" ===== sparkSession.sql(\"insert into emp values(120,'Prakash','Devlopment',10000)\") ====== ")
  sparkSession.sql("insert into emp values(121,'Pramod','Devlopment',90000)")
  mySqlDF.sqlContext.sql("select * from emp where eid > 105").show()
}
