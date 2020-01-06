package com.info.SparkRDD

object ZipTransformationExample extends App {

  /**
   * Zips this RDD with another one, returning key-value pairs with the first element in each RDD,
   * second element in each RDD, etc.
   *
   * Note: Both RDD must contain same number of elements
   */
  val sc = MySparkContextObject.getSparkContextObject
  val numberRDD = sc.parallelize(List(1,2,3,4,5,6,7,8,9))
  val nameRDD = sc.parallelize(List("Amit","Bipul","Chandan","Deepak","Emmy","Frank","Gouri","Hari","Ishan"))

  println("===== Number RDD ==== ")
  numberRDD.foreach(println)
  println("===== Name RDD ==== ")
  nameRDD.foreach(println)

  println("===== numberRDD.zip(nameRDD) RDD ====")
  val zipRDD = numberRDD.zip(nameRDD)
  zipRDD.foreach(println)

  /**
   * zipPartitions(RDD<B> rdd2,
   * boolean preservesPartitioning,
   * scala.Function2<scala.collection.Iterator<T>,scala.collection.Iterator<B>,scala.collection.Iterator<V>> f,
   * scala.reflect.ClassTag<B> evidence$11,
   * scala.reflect.ClassTag<V> evidence$12)
   * Zip this RDD's partitions with one (or more) RDD(s) and return a new RDD by applying a function to
   * the zipped partitions.
   */
  val numberRDD1 = sc.parallelize(List(1,2,3,4,5,6,7,8,9),2)
  val nameRDD1 = sc.parallelize(List("AmitZ","BipulZ","ChandanZ","DeepakZ","EmmyZ","FrankZ","GouriZ","HariZ","IshanZ"),2)
  println("===== numberRDD1.zipPartitions(nameRDD1,true)(myfunc) RDD ==== ")
  val zipRDD1 = numberRDD1.zipPartitions(nameRDD1,true)(myZipPartitionFunc)
  zipRDD1.foreach(println)

  def myZipPartitionFunc(aiter: Iterator[Int], biter: Iterator[String]): Iterator[String] = {
    var res = List[String]()
    while (aiter.hasNext && biter.hasNext) {
      val x = aiter.next + "-" + biter.next
      res ::= x
    }
    res.iterator
  }

  println("================ Zip with 3 RDD of int ===========")
  val a = sc.parallelize(0 to 9, 3)
  val b = sc.parallelize(10 to 19, 3)
  val c = sc.parallelize(100 to 109, 3)
  def myfunc(aiter: Iterator[Int], biter: Iterator[Int], citer: Iterator[Int]): Iterator[String] =
  {
    var res = List[String]()
    while (aiter.hasNext && biter.hasNext && citer.hasNext)
    {
      val x = aiter.next + " " + biter.next + " " + citer.next
      res ::= x
    }
    res.iterator
  }
  val resultRDD = a.zipPartitions(b, c)(myfunc).collect
  resultRDD.foreach(println)
}
