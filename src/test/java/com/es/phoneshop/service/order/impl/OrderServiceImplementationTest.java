package com.es.phoneshop.service.order.impl;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplementationTest {

    @Mock
    private Cart cart;

    private OrderServiceImplementation orderServiceImplementation;

    @Before
    public void setUp() throws Exception {
        ArrayList<CartItem> cartItems=new ArrayList<>();
        Product product1=new Product();
        product1.setId(1l);
        product1.setCode("code1");
        product1.setPrice(BigDecimal.valueOf(1));
        cartItems.add(new CartItem(product1,1));
        Product product2=new Product();
        product2.setId(2l);
        product2.setCode("code2");
        product2.setPrice(BigDecimal.valueOf(1));
        cartItems.add(new CartItem(product2,1));
        when(cart.getItems()).thenReturn(cartItems);
        when(cart.getTotalPrice()).thenReturn(BigDecimal.valueOf(2));
        orderServiceImplementation=OrderServiceImplementation.getOrderService();
    }

    @Test
    public void create() {
        Order actualOrder=orderServiceImplementation.create(cart);
        assertTrue(actualOrder.getSubtotal().equals(BigDecimal.valueOf(2)));
    }

    @Test
    public void getOrderBySecureId() {
    }

    @Test
    public void placeOrder() {
    }

    @Test
    public void getOrder() {
        Order savedOrder=orderServiceImplementation.create(cart);
        Order actualOrder = orderServiceImplementation.getOrder(0);
        System.out.println("ddd");
    }
}