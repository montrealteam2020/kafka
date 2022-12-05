package com.bank.springboot.config;

import com.bank.springboot.model.Account;
import com.bank.springboot.model.Transaction;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class CustomSerdes {

    private CustomSerdes() {}

    public static Serde<Account> accountSerde() {
        JsonSerializer<Account> serializer = new JsonSerializer<>();
        JsonDeserializer<Account> deserializer = new JsonDeserializer<>(Account.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<Transaction> transactionSerde() {
        JsonSerializer<Transaction> serializer = new JsonSerializer<>();
        JsonDeserializer<Transaction> deserializer = new JsonDeserializer<>(Transaction.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

 }