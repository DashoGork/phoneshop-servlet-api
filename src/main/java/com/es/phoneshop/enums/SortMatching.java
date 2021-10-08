package com.es.phoneshop.enums;

public enum SortMatching {
    ANY(1),
    ALL(-1);

    private int value;

    SortMatching(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
