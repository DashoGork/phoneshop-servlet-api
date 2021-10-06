package com.es.phoneshop.dao.order.impl;

import com.es.phoneshop.model.order.Order;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayListOrderDaoTest {

    private ArrayListOrderDao dao;
    private Order order;

    @Before
    public void setUp() throws Exception {
        dao = ArrayListOrderDao.getArrayListProductDao();
        order = new Order();
        order.setFirstName("name");
        order.setSecureId("secureId");
        dao.saveItem(order);
    }

    @Test
    public void getOrderBySecureId() {
        Order actualOrder = dao.getOrderBySecureId("secureId");
        assertTrue(actualOrder.getSecureId().equals(order.getSecureId()));
        assertTrue(actualOrder.getFirstName().equals(order.getFirstName()));
    }

    @Test
    public void save() {
        Order orderToSave = new Order();
        orderToSave.setSecureId("secure");
        orderToSave.setFirstName("name1");
        dao.save(orderToSave);
        assertTrue(dao.getObjects().size() == 1);
    }
}