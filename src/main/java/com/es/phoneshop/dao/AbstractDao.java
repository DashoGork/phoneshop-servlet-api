package com.es.phoneshop.dao;

import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.BaseModelEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractDao<T extends BaseModelEntity> {

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();
    private List<T> objects;
    private long id;

    public List<T> getObjects() {
        return objects;
    }

    public AbstractDao() {
        this.objects = new ArrayList<T>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public T getItem(Long id){
        readLock.lock();
        try {
            if (id != null) {
                T object;
                object = objects.stream().
                        filter((ob -> id.equals(ob.getId()))).
                        findFirst().orElseThrow(() -> new OrderNotFoundException(String.format("BaseEntity with id = %d  can't be found.", id)));
                return object;
            } else
                throw new IllegalArgumentException("id is null");
        } finally {
            readLock.unlock();
        }
    }

    public void saveItem(T baseEntity){
        writeLock.lock();
        try {
            if (baseEntity != null) {
                if (objects.stream()
                        .anyMatch(orderFromStream -> orderFromStream.getId().equals(baseEntity.getId()))) {

                } else {
                    baseEntity.setId(id++);
                    objects.add(baseEntity);
                }
            }
        } finally {
            writeLock.unlock();
        }
    }
}
