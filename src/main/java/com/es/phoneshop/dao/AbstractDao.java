package com.es.phoneshop.dao;

import com.es.phoneshop.exceptions.BaseModelNotFoundException;
import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractDao<T extends BaseModel, E extends BaseModelNotFoundException> {

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

    public T getItem(Long id) throws E {
        readLock.lock();
        try {
            if (id != null) {
                T object;
                object = objects.stream().
                        filter((ob -> id.equals(ob.getId()))).
                        findFirst().orElseThrow(() -> new BaseModelNotFoundException(String.format("BaseEntity with id = %d  can't be found.", id)));
                return object;
            } else
                throw new IllegalArgumentException("id is null");
        } finally {
            readLock.unlock();
        }
    }

    public void saveItem(T object) {
        writeLock.lock();
        try {
            if (object != null) {
                object.setId(id++);
                objects.add(object);
            }
        } finally {
            writeLock.unlock();
        }
    }
}
