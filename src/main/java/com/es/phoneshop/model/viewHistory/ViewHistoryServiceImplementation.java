package com.es.phoneshop.model.viewHistory;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ViewHistoryServiceImplementation implements ViewHistoryService{

    private ViewHistory history;
    private static ViewHistoryServiceImplementation viewHistoryServiceImplementation;
    private static final String VIEW_HISTORY_SESSION_ATTRIBUTE=ViewHistoryServiceImplementation.class.getName()+".viewHistory";

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
        ViewHistory viewHistory=(ViewHistory) request.getSession().getAttribute(VIEW_HISTORY_SESSION_ATTRIBUTE);
        if(viewHistory==null){
            viewHistory=new ViewHistory();
            request.getSession().setAttribute(VIEW_HISTORY_SESSION_ATTRIBUTE,viewHistory);
        }
        return viewHistory;
    }

    @Override
    public synchronized void add(ViewHistory history, Product product) {
        List<Product> viewHistory=history.getViewHistory();
        if(!viewHistory.contains(product)){
            history.getViewHistory().add(0,product);
            if(history.getViewHistory().size()>3){
                history.getViewHistory().remove(3);
            }
        }
    }
}
