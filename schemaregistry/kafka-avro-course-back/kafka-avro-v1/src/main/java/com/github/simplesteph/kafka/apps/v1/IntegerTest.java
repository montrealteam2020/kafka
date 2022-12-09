package com.github.simplesteph.kafka.apps.v1;

public class IntegerTest {

    public static void main(String[] args) {

        Integer code = new Integer("0010");
        int decimalExample = Integer.parseInt("20");
        int signedPositiveExample = Integer.parseInt("+20");
        int signedNegativeExample = Integer.parseInt("-20");

        System.out.println("code = "+code);

    }
}
