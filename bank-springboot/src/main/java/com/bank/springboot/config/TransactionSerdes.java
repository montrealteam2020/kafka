package com.bank.springboot.config;

import com.bank.springboot.model.Transaction;
import org.apache.kafka.common.serialization.Serde;
import org.springframework.context.annotation.Bean;


public class TransactionSerdes {

    @Bean
    public Serde<Transaction> transactionSerde() {
        Serde<Transaction> transactionSerde =
                     org.apache.kafka.common.serialization.Serdes.serdeFrom(
                             Transaction.class);

            return transactionSerde;

    }

}
