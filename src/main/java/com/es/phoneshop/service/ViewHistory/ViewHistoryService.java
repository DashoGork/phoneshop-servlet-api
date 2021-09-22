package com.es.phoneshop.service.ViewHistory;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.viewHistory.ViewHistory;

import javax.servlet.http.HttpServletRequest;

public interface ViewHistoryService {
    ViewHistory getViewHistory(HttpServletRequest request);
    void add(ViewHistory history, Product product);
}
