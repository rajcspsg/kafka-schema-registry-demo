package com.example;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerV2 {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "kafka1.com:9092,kafka2.com:9092,kafka3.com:9092");
        props.setProperty("group.id", System.currentTimeMillis() + "");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.offset.reset", "earliest");
        props.setProperty("key.deserializer", StringDeserializer.class.getName());
        props.setProperty("value.deserializer", KafkaAvroDeserializer.class.getName());
        props.setProperty("schema.registry.url", "http://schemaregistry1.com:8081");
        props.setProperty("specific.avro.reader", "true");

        try(KafkaConsumer<String, Customer> consumer = new KafkaConsumer<String, Customer>(props)) {
            String topic = "customer-avro";
            consumer.subscribe(Collections.singleton(topic));
            System.out.println("Waiting for data...");
            while (true) {
                ConsumerRecords<String, Customer> records = consumer.poll(1500);
                for (ConsumerRecord<String, Customer> record : records) {
                    record.key();
                    Customer customer = record.value();
                    System.out.println("customer "+ customer);
                }
                consumer.commitSync();

            }
        }
    }
}
