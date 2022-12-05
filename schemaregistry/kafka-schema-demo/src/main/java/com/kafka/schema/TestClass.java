package com.kafka.schema;

public class TestClass {

    public static void main(String[] args) {
        Users user1 = new Users();
        user1.setAge("28");
        user1.setName("Ala");
        user1.setPhone("12345");
        Users user2 = new Users();
        user2.setAge("28");
        user2.setName("Ala");
        user2.setPhone("678909");
        if(user1.equals(user2)){
            System.out.println("Equals");
        }else
        {
            System.out.println("not Equals");
        }
    }
}
