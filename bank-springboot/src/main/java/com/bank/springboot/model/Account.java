package com.bank.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Account {

  String name;
  Integer balance;
  String  time ;
  int count;

  public Account() {

  }
}
