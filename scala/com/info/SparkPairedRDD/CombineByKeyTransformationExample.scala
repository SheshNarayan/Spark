package com.info.SparkPairedRDD

import com.info.SparkRDD.MySparkContextObject

/**
 * https://backtobazics.com/big-data/apache-spark-combinebykey-example/
 *
 * http://apachesparkbook.blogspot.com/2015/12/combiner-in-pair-rdds-combinebykey.html
 * 1st Argument : createCombiner is called when a key(in the RDD element) is found for the first time in a given Partition. This method creates an initial value for the accumulator for that key
 * 2nd Argument : mergeValue is called when the key already has an accumulator
 * 3rd Argument : mergeCombiners is called when more that one partition has accumulator for the same key
 *
 */
object CombineByKeyTransformationExample extends  App{
  val sc = MySparkContextObject.getSparkContextObject

  val stockRDD = sc.textFile("D:\\SparkPractice\\SparkData\\stocks1")
  val stockSP = stockRDD.map(rec=>(rec.split("\\t")(1),rec.split("\\t")(3).toDouble))

//  val combineByKeyRDD = stockSP.combineByKey( price=> (price,1.0), (acc: (Double, Double), v) => (acc._1 + v, acc._2 + 1.0),
//                                 (acc1: (Double, Double), acc2: (Double, Double)) =>   (acc1._1 + acc2._1, acc1._2 + acc2._2))
// Same As
  val combineByKeyRDD = stockSP.combineByKey(myCreateCombiner, myMergeValue, myMergeCombiner)
  combineByKeyRDD foreach println
  println("================= Average ============== ")
  val avgResultRDD = combineByKeyRDD.map(rec=>(rec._1, rec._2._1/rec._2._2))
  avgResultRDD foreach println

  def myCreateCombiner(value: Double) : (Double,Double) ={
      // data, count
        return (value,1.0)
      }

  // acc1(dataValue, dataCount), NewDataValue
  def myMergeValue(acc1: (Double, Double), value:Double) : (Double, Double) = {
    return (acc1._1 + value, acc1._2 + 1.0)
  }

  // Combine partitions data (DataValue, Count)
  def myMergeCombiner(acc1: (Double,Double), acc2: (Double,Double) ) :  (Double,Double) = {
    return ( acc1._1 + acc2._1, acc1._2+acc2._2 )
  }


  // Creating PairRDD studentRDD with key value pairs
  val studentRDD = sc.parallelize(Array(
    ("Joseph", "Maths", 83), ("Joseph", "Physics", 74), ("Joseph", "Chemistry", 91),
    ("Joseph", "Biology", 82), ("Jimmy", "Maths", 69), ("Jimmy", "Physics", 62),
    ("Jimmy", "Chemistry", 97), ("Jimmy", "Biology", 80), ("Tina", "Maths", 78),
    ("Tina", "Physics", 73), ("Tina", "Chemistry", 68), ("Tina", "Biology", 87),
    ("Thomas", "Maths", 87), ("Thomas", "Physics", 93), ("Thomas", "Chemistry", 91),
    ("Thomas", "Biology", 74), ("Cory", "Maths", 56), ("Cory", "Physics", 65),
    ("Cory", "Chemistry", 71), ("Cory", "Biology", 68), ("Jackeline", "Maths", 86),
    ("Jackeline", "Physics", 62), ("Jackeline", "Chemistry", 75), ("Jackeline", "Biology", 83),
    ("Juan", "Maths", 63), ("Juan", "Physics", 69), ("Juan", "Chemistry", 64),
    ("Juan", "Biology", 60)), 3)

  //Defining createCombiner, mergeValue and mergeCombiner functions
  def createCombiner = (tuple: (String, Int)) =>
    (tuple._2.toDouble, 1)

  def mergeValue = (accumulator: (Double, Int), element: (String, Int)) =>
    (accumulator._1 + element._2, accumulator._2 + 1)

  def mergeCombiner = (accumulator1: (Double, Int), accumulator2: (Double, Int)) =>
    (accumulator1._1 + accumulator2._1, accumulator1._2 + accumulator2._2)


  // use combineByKey for finding percentage
  val combRDD = studentRDD.map(t => (t._1, (t._2, t._3)))
    .combineByKey(createCombiner, mergeValue, mergeCombiner)
    .map(e => (e._1, e._2._1/e._2._2))

  //Check the Output
  //combRDD.collect foreach println
}

/* For Keywise aggregation
 def myCreateCombiner(key: Double) : Double ={
        return key
      }
  def myMergeValue(acc1: Double, acc2: Double) :  Double = {
    return acc1 + acc2
  }

  def myMergeCombiner(acc1: Double,acc2:  Double) : Double = {
    return   acc1+acc2
  }
 */