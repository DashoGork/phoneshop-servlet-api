package com.es.phoneshop.model.viewHistory;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ViewHistoryService {
    ViewHistory getViewHistory(HttpServletRequest request);
    void add(ViewHistory history, Product product);
}
