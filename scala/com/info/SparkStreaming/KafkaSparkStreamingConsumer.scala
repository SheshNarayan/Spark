package com.info.SparkStreaming

import java.util.Properties

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.spark.{SparkConf, streaming}
import org.apache.spark.streaming.{Duration, StreamingContext}
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

import scala.collection.JavaConversions._

object KafkaSparkStreamingConsumer extends App{

  val conf = new SparkConf().setAppName("Spark Streaming").setMaster("local[2]")
  //Creating StreamingContext Object
  val streamingContext = new StreamingContext(conf, streaming.Duration(5000))
  streamingContext.sparkContext.setLogLevel("WARN")
  //streamingContext.remember(Duration(10000)) //Clearing old data after

  val topicName = Set("stocks")
  val kafkaProps = new Properties()
  kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092")
  kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer")
  kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer")
  kafkaProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest") //earliest, latest, none -- none is default
  kafkaProps.put(ConsumerConfig.GROUP_ID_CONFIG,"Kafka-Standalone-Consumer-Group") // user define uniquename

//  or use Map like
//val kafkaProps1 =Map(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG->"localhost:9092",
  //                   ConsumerConfig.AUTO_OFFSET_RESET_CONFIG->"earliest")

  // Creating Input Stream
  val inputKafkaDStream = KafkaUtils.createDirectStream(streamingContext,
    LocationStrategies.PreferConsistent,
    ConsumerStrategies.Subscribe[String, String](topicName, kafkaProps) )

  val messageStream: DStream[String] = inputKafkaDStream.map(record=> record.value()) // Value
  val stockStream: DStream[String] = messageStream.map(rec=>rec.split("\\t")(1)) // for Key

  //messageStream.print()
//  stockStream.print()

  stockStream.foreachRDD(stockRDD=>{
    if(!stockRDD.isEmpty()){

      stockRDD.foreachPartition(partition=>{
        val topicName = "market"
        val kafkaProps = new Properties()

        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092")
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
        kafkaProps.put(ProducerConfig.ACKS_CONFIG,"1")
        //creating producer
        val producer = new KafkaProducer[String, String](kafkaProps)
        partition.foreach(message=>{
          val producerRecord = new ProducerRecord[String, String](topicName, null, message+":MyData")
          producer.send(producerRecord)
        })
      })
    }

  })
  streamingContext.start()
  streamingContext.awaitTermination()


}
