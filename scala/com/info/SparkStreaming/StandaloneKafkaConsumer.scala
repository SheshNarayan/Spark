package com.info.SparkStreaming

import java.util.Properties

import scala.collection.JavaConversions._
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}

object StandaloneKafkaConsumer extends App{

  val topicName = Set("stocks")
  val kafkaProps = new Properties()
  kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092")
  kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer")
  kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer")

  kafkaProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest") //earliest, latest, none -- none is default
  kafkaProps.put(ConsumerConfig.GROUP_ID_CONFIG,"Kafka-Standalone-Consumer-Group") // user define uniquename

  val consumer = new KafkaConsumer[String,String](kafkaProps)
  val consumerRecord = consumer.subscribe(topicName)

  while(true){
    val records = consumer.poll(100)
    records.foreach(record=>{
//      println(record.offset())
//      println(record.key())
//      println(record.value())
//      println(record.partition())
//      println("==========================")

      println(record.offset()+"\t"+record.key()+"\t"+record.value()+"\t"+record.partition())
    })
  }

}
