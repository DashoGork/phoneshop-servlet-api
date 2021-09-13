package com.es.phoneshop.web;

public enum SortOrder {
    asc(-1),
    desc(1);

    private int value;

    SortOrder(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
