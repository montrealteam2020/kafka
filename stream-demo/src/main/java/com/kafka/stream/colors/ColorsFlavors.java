package com.kafka.stream.colors;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Slf4j
public class ColorsFlavors {

    public static void main(String[] args) {
        // Creating Arrays of String type
        String a[]
                = new String[] { "red","green","blue"};

        List<String> listColor=  Arrays.asList(a);
        //Properties
        Properties config = new Properties();
        config.setProperty(StreamsConfig.APPLICATION_ID_CONFIG,"colors-application");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        //StreamBuilder
        StreamsBuilder builder = new StreamsBuilder();

        // create topics
        //Read Stream from topics  colors-input
        KStream<String,String> colorsIn= builder.stream("colors-input-test");
        //filter
        colorsIn.filter((key,value)-> listColor.contains(value))
                .peek((key,value)->log.info("Key:  "+key +"value:  "+value));
        // Write to KTable
        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();

        // shutdown hook to correctly close the streams application
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
