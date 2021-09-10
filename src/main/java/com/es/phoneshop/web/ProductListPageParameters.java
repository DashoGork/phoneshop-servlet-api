package com.es.phoneshop.web;

public enum ProductListPageParameters {
    query(new String[]{}),
    sort(new String[]{"description", "price"}),
    order(new String[]{"asc", "desc"});

    private String[] values;

    ProductListPageParameters(String[] values) {
        this.values = values;
    }

    public String[] getValues() {
        return values;
    }
}
