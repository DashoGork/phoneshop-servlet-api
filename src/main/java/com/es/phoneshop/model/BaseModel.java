package com.es.phoneshop.model;

public class BaseModel {
    private Long id;

    public BaseModel(Long id) {
    }

    public BaseModel() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
