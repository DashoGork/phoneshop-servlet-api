package com.es.phoneshop.model;

public class BaseModelEntity {
    private Long id;

    public BaseModelEntity(Long id) {
    }

    public BaseModelEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
