package com.es.phoneshop.exceptions;

public class ProductNotFoundException extends BaseModelNotFoundException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
