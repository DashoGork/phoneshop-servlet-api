package com.es.phoneshop.exceptions;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String s) {
        super(s);
    }
}
