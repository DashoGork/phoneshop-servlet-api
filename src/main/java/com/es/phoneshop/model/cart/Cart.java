package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<CartItem> items;

    private int totalQuantity;
    private BigDecimal totalPrice;

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItem(CartItem cartItem) {
        items.add(cartItem);
    }

    public List<Product> getProducts(){
        List<Product> productsFromCart=new ArrayList<>();
        for (CartItem cartItem: items) {
            productsFromCart.add(cartItem.getProduct());
        }
        return productsFromCart;
    }

    @Override
    public String toString() {
        return items.toString();
    }


}
