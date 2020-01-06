package com.info.SparkPairedRDD
import com.info.SparkRDD.MySparkContextObject
object AaggregateByKeyTranformationExample extends App {

  /** https://backtobazics.com/big-data/spark/apache-spark-aggregatebykey-example/
   * seqOp function will be applied to each element of the PairRDD[String, (String, Double)] .
   * As a result, it gives the maximum marks of a student out of all subjects.
   * As your RDD is distributed, it could be possible that multiple partitions may have records of a single student.
   * Hence, seqOp function is applied to a single partition and finds the maximum marks from that partition.
   *
   * So what about finding maximum marks from different partitions?
   *
   * Remember function combOp ? It does take care of finding maximum marks across multiple partitions for a student.
   * combOp function will be applied to all aggregated values of different partitions (i.e. output of seqOp ).
   * And finally we have an aggregated value with respect to a single key.
   */
  val sc = MySparkContextObject.getSparkContextObject

  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks")
  val stockSP = stockRDD.map(rec=>(rec.split("\\t")(1),rec.split("\\t")(3).toDouble))
  println(stockSP)
  val resultRDD = stockSP.map(rec=>(rec._1,rec._2)).aggregateByKey(0.0)(seqOpFun,accOpFun)

  // Perform on each partition-- Here noth will be same
  def seqOpFun(acc: Double, record: Double) : Double = {
    return record+acc
  }
  // Combine the result of each partition
  def accOpFun(acc: Double, recValue: Double) : Double = {
    var result:Double = acc + recValue;
    return result
  }
  resultRDD.foreach(println)
  println(resultRDD.count())
  //  def seqOpFun = (acc: Int, record: (String, Double)) => record._2



  // For Multiple values as Tuple
//  val resultRDD = stockSP.map(rec=>(rec._1,(rec._1, rec._2))).aggregateByKey(0.0)(seqOpFun,accOpFun)
//  //stockSP.take(5).foreach(println)
//  def seqOpFun(acc: Double, record: (String, Double)) : Double = {
//    return record._2
//  }
//  resultRDD.foreach(println)
////  def seqOpFun = (acc: Int, record: (String, Double)) => record._2
//  def accOpFun(acc: Double, recValue: Double) : Double = {
//        var result:Double = acc + recValue;
//        return result
//  }
}
