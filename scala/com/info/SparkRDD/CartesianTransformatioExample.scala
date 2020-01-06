package com.info.SparkRDD

object CartesianTransformatioExample extends App{

  /**
   * public <U> RDD<scala.Tuple2<T,U>> cartesian(RDD<U> other, scala.reflect.ClassTag<U> evidence$5)
   * Return the Cartesian product of this RDD and another one, that is,
   * the RDD of all pairs of elements (a, b) where a is in this and b is in other.
   */
  val sc = MySparkContextObject.getSparkContextObject

  val nameRDD = sc.parallelize(List("Vimlesh","Shesh","Naren","Santosh"))
  val amountRDD = sc.parallelize( Array(100,200,300))

  val cartesianRDD = nameRDD.cartesian(amountRDD)
//  cartesianRDD.map(println)
  cartesianRDD.foreach(println)
}
