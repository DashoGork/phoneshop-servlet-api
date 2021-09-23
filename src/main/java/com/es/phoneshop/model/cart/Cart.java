package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

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
