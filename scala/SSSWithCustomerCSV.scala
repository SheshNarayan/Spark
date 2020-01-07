/**
https://www.kaggle.com/amark720/retail-shop-case-study-dataset/version/1#
https://spark.apache.org/docs/latest/sql-reference.html#data-types

customer_Id	DOB	Gender	city_code
268408,	02-01-1970,	M,	4
269696,	07-01-1970,	F,	8

*/

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.{OutputMode}
import org.apache.spark.sql.types._

object SSSWithCustomerCSV extends App {

	//Creating Spark Session Object
	val sparkSession =  SparkSession.build()
								    .appName("Spark_structure-Streaming")
									.master("yarn") //for local mode use local[*]
									.config("spark.sql.warehouse.dir","file:///home/data/warehouseDir")
									.getOrCreate()
	
	// Creating Schema
	val customerSchema = new StructType().add("customer_Id",IntegerType)
									.add("DOB",TimestampType) //DateType	java.sql.Date	
									.add("Gender",StringType)
									.add("city_code",ShortType)
	

	// Reading Streaming data from CSV file- When new file reach in folder it will excute new batch
	val custDF = sparkSession.readStream
							 .option("header", "true")
							 .schema(customerSchema)
							 .csv("file:///home/data/warehouseDir/streaming")
	
	val filterDF = custDF.filter("Gender='M'" || "city_code=4")
	
	val query = filterDF.writeStream
						.format("console") // to print on console
						.queryName("CustomerQuery") // Create temp Table **check this
						.option("truncate","false") // do not turncate record it exceeds from line
						.outputMode("update") //can be append or complete - complete will not work in this case
						.start() // start the query execution
						.awaitTermination() // execute continous...
	
}

