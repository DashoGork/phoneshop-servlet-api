package com.es.phoneshop.dao.order.impl;

import com.es.phoneshop.dao.AbstractDao;
import com.es.phoneshop.dao.order.OrderDao;
import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

public class ArrayListOrderDao extends AbstractDao<Order> implements OrderDao {

    private static volatile ArrayListOrderDao arrayListProductDao;

    public static ArrayListOrderDao getArrayListProductDao() {
        ArrayListOrderDao result = arrayListProductDao;
        if (result != null) {
            return result;
        }
        synchronized (ArrayListOrderDao.class) {
            if (arrayListProductDao == null) {
                arrayListProductDao = new ArrayListOrderDao();
            }
            return arrayListProductDao;
        }
    }

    @Override
    public Order getOrderBySecureId(String secureId) {
        return super.getObjects().stream().filter((order -> order.getSecureId().equals(secureId)))
                .findFirst().orElseThrow
                        (() -> new OrderNotFoundException(String.format("Order with secureId =   can't be found.", secureId)));
    }

    @Override
    public void save(Order order) {
        saveItem(order);
    }
}

