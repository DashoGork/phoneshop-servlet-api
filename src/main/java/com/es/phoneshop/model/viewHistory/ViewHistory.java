package com.es.phoneshop.model.viewHistory;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class ViewHistory implements Serializable {
    private static final long serialVersionUID = 1L;

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
