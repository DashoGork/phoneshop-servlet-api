package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return product.getDescription() + " " + quantity;
    }
}
