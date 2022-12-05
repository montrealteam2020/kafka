package com.kafka.schema;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaAvroProducer {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        prop.setProperty(ProducerConfig.ACKS_CONFIG,"1");
        prop.setProperty(ProducerConfig.RETRIES_CONFIG,"10");
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        prop.setProperty("schema.registry.url","http://127.0.0.1:8081");
        KafkaProducer<String,Customer> producer = new KafkaProducer<String, Customer>(prop);
        String topic = "customer-avro";

        Customer customer =  Customer.newBuilder()
        .setAge(18)
        .setAutomatedEmail(true)
        .setWeight(22.5f)
        .setHeight(180f)
        .setLastName("Daouadji")
        .setFirstName("Abdel").build();
                ProducerRecord<String,Customer> producerRecord = new ProducerRecord<String, Customer>(
                        topic, customer
                );
        producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
                    if(exception ==null){
                        System.out.println("Success");
                        System.out.println(recordMetadata.toString());
                    }
                    }
                });

       producer.flush();
        producer.close();

    }
}
