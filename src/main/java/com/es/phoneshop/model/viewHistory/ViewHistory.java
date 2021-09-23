package com.es.phoneshop.model.viewHistory;

import com.es.phoneshop.model.product.Product;

import java.util.LinkedList;
import java.util.Queue;

public class ViewHistory {
    private Queue<Product> productHistoryEntities;

    public ViewHistory() {
        this.productHistoryEntities = new LinkedList<>();
    }

    public void addProductInHistory(Product product) {
        productHistoryEntities.add(product);
    }

    public Queue<Product> getViewHistory() {
        return productHistoryEntities;
    }

}
