package com.es.phoneshop.service.ViewHistory;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.viewHistory.ViewHistory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ViewHistoryServiceImplementation implements ViewHistoryService {

    private ViewHistory history;
    private static volatile ViewHistoryServiceImplementation viewHistoryServiceImplementation;
    private static final String VIEW_HISTORY_SESSION_ATTRIBUTE = ViewHistoryServiceImplementation.class.getName() + ".viewHistory";

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();

    private ViewHistoryServiceImplementation() {
        history = new ViewHistory();
    }

    public static ViewHistoryServiceImplementation getViewHistoryServiceImplementation() {
        ViewHistoryServiceImplementation result = viewHistoryServiceImplementation;
        if (result != null) {
            return result;
        }
        synchronized (ViewHistoryServiceImplementation.class) {
            if (viewHistoryServiceImplementation == null) {
                viewHistoryServiceImplementation = new ViewHistoryServiceImplementation();
            }
            return viewHistoryServiceImplementation;
        }
    }

    @Override
    public synchronized ViewHistory getViewHistory(HttpServletRequest request) {
        readLock.lock();
        try {
            ViewHistory viewHistory = (ViewHistory) request.getSession().getAttribute(VIEW_HISTORY_SESSION_ATTRIBUTE);
            if (viewHistory == null) {
                viewHistory = new ViewHistory();
                request.getSession().setAttribute(VIEW_HISTORY_SESSION_ATTRIBUTE, viewHistory);
            }
            return viewHistory;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void add(ViewHistory history, Product product) {
        writeLock.lock();
        try {
            List<Product> viewHistory = history.getViewHistory();
            if (!viewHistory.contains(product)) {
                history.getViewHistory().add(0, product);
                if (history.getViewHistory().size() > 3) {
                    history.getViewHistory().remove(3);
                }
            }
        } finally {
            writeLock.unlock();
        }
    }
}
