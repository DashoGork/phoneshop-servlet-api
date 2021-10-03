package com.es.phoneshop.service.order;

import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    Order create(Cart cart);
    List<PaymentMethod> getPaymentMethods();
    void placeOrder(Order order);
    Order getOrder(long id);
    Order getOrderBySecureId(String secureId);
}
