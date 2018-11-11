package com.example;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

public class KafkaProducerV2 {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "kafka1.com:9092");
        props.setProperty("acks", "1");
        props.setProperty("retries", "10");
        props.setProperty("key.serializer", StringSerializer.class.getName());
        props.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        props.setProperty("schema.registry.url", "http://schemaregistry1.com:8081");
        KafkaProducer<String, Customer> kafkaProducer = new KafkaProducer<String, Customer>(props);
        String topic = "customer-avro";
        Customer customer = Customer.newBuilder()
                .setFirstName("Mottu")
                .setAge(24)
                .setLastName("kalidasan")
                .setHeight(5.4f)
                .setWeight(6.4f).build();
        ProducerRecord<String, Customer> producerRecord = new ProducerRecord<String, Customer>(topic, customer);
        kafkaProducer.send(producerRecord, (metadata, excption) -> {
            if(excption == null) {
                System.out.println("Success!");
                System.out.println(metadata.toString());
            } else {
                excption.printStackTrace();
            }
        });
        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
