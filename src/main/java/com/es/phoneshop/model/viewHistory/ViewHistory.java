package com.es.phoneshop.model.viewHistory;

import com.es.phoneshop.model.product.Product;

import java.util.LinkedList;
import java.util.List;

public class ViewHistory {
    private List<Product> productHistory;

    public ViewHistory() {
        this.productHistory = new LinkedList<>();
    }

    public void addProductInHistory(Product product){
        productHistory.add(product);
    }

    public List<Product> getViewHistory() {
        return productHistory;
    }

}
