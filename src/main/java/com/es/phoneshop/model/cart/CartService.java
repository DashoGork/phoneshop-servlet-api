package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.lang.reflect.Array;

public interface CartService {
    Cart getCart();
    void add(Product product, int quantity);
}
