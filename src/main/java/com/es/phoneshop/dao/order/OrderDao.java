package com.es.phoneshop.dao.order;

import com.es.phoneshop.model.order.Order;

public interface OrderDao {
    void save(Order order);
    Order getItem(Long id);
    Order getOrderBySecureId(String strung);
}
