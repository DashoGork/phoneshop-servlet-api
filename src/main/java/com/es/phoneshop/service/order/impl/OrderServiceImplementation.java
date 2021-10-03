package com.es.phoneshop.service.order.impl;

import com.es.phoneshop.dao.order.OrderDao;
import com.es.phoneshop.dao.order.impl.ArrayListOrderDao;
import com.es.phoneshop.dao.product.ProductDao;
import com.es.phoneshop.dao.product.impl.ArrayListProductDao;
import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.order.OrderService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class OrderServiceImplementation implements OrderService {

    private OrderDao orderDao;
    private static volatile OrderServiceImplementation orderServiceImplementation;
    private static final String CART_SESSION_ATTRIBUTE = OrderServiceImplementation.class.getName() + ".cart";

    private OrderServiceImplementation() {
        orderDao = ArrayListOrderDao.getArrayListProductDao();
    }

    public static OrderServiceImplementation getOrderService() {
        OrderServiceImplementation result = orderServiceImplementation;
        if (result != null) {
            return result;
        }
        synchronized (OrderServiceImplementation.class) {
            if (orderServiceImplementation == null) {
                orderServiceImplementation = new OrderServiceImplementation();
            }
            return orderServiceImplementation;
        }
    }

    public Order create(Cart cart) {
        synchronized (cart) {
            Order order = new Order();
            order.setItems(cart.getItems().stream().map((item -> {
                try {
                    return (CartItem) item.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            })).collect(Collectors.toList()));
            order.setTotalQuantity(cart.getTotalQuantity());
            order.setSubtotal(cart.getTotalPrice());
            order.setDeliveryCost(calculateDeliveryCost());
            order.setTotalPrice(order.getSubtotal().add(order.getDeliveryCost()));
            return order;
        }
    }

    @Override
    public Order getOrderBySecureId(String secureId) {
        return orderDao.getOrderBySecureId(secureId);
    }

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    @Override
    public void placeOrder(Order order) {
        order.setSecureId(UUID.randomUUID().toString());
        orderDao.save(order);
    }

    @Override
    public Order getOrder(long id) {
        return orderDao.getItem(id);
    }

    private BigDecimal calculateDeliveryCost(){
        return new BigDecimal("10");
    }
}
