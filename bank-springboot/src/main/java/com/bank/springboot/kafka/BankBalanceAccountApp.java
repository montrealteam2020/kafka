package com.bank.springboot.kafka;

import com.bank.springboot.config.CustomSerdes;
import com.bank.springboot.model.Account;
import com.bank.springboot.model.Transaction;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.connect.json.JsonDeserializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.time.Instant;
import java.util.Properties;

public class BankBalanceAccountApp {




    public static void main(String[] args) {
        Properties config = new Properties();

        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "bank-balance-application");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // we disable the cache to demonstrate all the "steps" involved in the transformation - not recommended in prod
        config.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0");

        // Exactly once processing!!
        config.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);

        // json Serde
        final Serializer<JsonNode> jsonSerializer = new JsonSerializer();
        final Deserializer<JsonNode> jsonDeserializer = new JsonDeserializer();
        final Serde<JsonNode> jsonSerde = Serdes.serdeFrom(jsonSerializer, jsonDeserializer);

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Transaction> bankTransactions = builder.stream("bank-transactions",
                Consumed.with(Serdes.String(), CustomSerdes.transactionSerde()));

       Account initialBalance = new Account();
        KTable<String, Account> bankBalance = bankTransactions
                .groupByKey(Grouped.with(Serdes.String(), CustomSerdes.transactionSerde()))
                .aggregate(
                        () -> initialBalance,
                        (key, transaction, balance) -> newBalance(transaction, balance),
                        Materialized.with(Serdes.String(), CustomSerdes.accountSerde())
                );

        bankBalance.toStream().to("bank-balance-exactly-once", Produced.with(Serdes.String(), CustomSerdes.accountSerde()));

        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.cleanUp();
        streams.start();

        // print the topology
        streams.localThreadsMetadata().forEach(data -> System.out.println(data));

        // shutdown hook to correctly close the streams application
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    private static Account newBalance(Transaction transaction, Account balance) {
        // create a new balance json object
        Account newBalance = new Account();
        newBalance.setCount (balance.getCount() + 1);
        newBalance.setBalance(balance.getBalance()+ transaction.getAmount());

        Instant now = Instant.now();
        newBalance.setTime(now.toString());
        return newBalance;
    }


}
