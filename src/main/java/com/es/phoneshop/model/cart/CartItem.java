package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartItem implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

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

    public Product getProduct(){
        return product;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public BigDecimal getTotalPrice(){
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
