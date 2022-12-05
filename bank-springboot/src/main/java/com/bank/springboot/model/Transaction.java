package com.bank.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {

    String name;
    Integer amount;
    String  time ;

    public Transaction() {

    }
}
