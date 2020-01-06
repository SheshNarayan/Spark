package com.info.SparkStreaming

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import scala.io.Source

object StandaloneKafkaProducer extends App{

  val inputFile = "D:\\SparkPractice\\SparkData\\stocks"
  // To read files from source files
  val stocks = Source.fromFile(inputFile).getLines()

  val topicName = "stocks"
  val kafkaProps = new Properties()

  kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092")
  //props.put("bootstrap.server","localhost:9092")

  kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
  // props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
  //  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  kafkaProps.put(ProducerConfig.ACKS_CONFIG,"1")

  //creating producer
  val producer = new KafkaProducer[String, String](kafkaProps)

  stocks.foreach(record=>{
    // Logic to write data Kafka Topic
    val key = record.split("\\t")(1)
    val producerRecord = new ProducerRecord[String, String](topicName, key, record)
    producer.send(producerRecord)
//    println(producerRecord)

  })


}
