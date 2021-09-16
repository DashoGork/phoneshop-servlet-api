package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class PriceHistory {
    private Map<GregorianCalendar, BigDecimal> priceHistory;

    public PriceHistory() {
        this.priceHistory = new HashMap<>();
    }

    public Map<GregorianCalendar, BigDecimal> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(Map<GregorianCalendar, BigDecimal> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public void addPriceHistory(GregorianCalendar date, BigDecimal price) {
        priceHistory.put(date,price);
    }

    @Override
    public String toString() {
        return "PriceHistory{" +
                "priceHistory=" + priceHistory +
                '}';
    }
}
