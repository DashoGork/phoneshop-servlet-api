package com.es.phoneshop.exceptions;

public class OrderNotFoundException extends BaseModelNotFoundException{
    public OrderNotFoundException(String s) {
        super(s);
    }
}
