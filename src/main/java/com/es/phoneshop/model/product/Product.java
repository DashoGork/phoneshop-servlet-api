package com.es.phoneshop.model.product;

import com.es.phoneshop.model.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class Product extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String description;
    /**
     * null means there is no price because the product is outdated or new
     */
    private BigDecimal price;
    /**
     * can be null if the price is null
     */
    private Currency currency;
    private int stock;
    private String imageUrl;
    private PriceHistory priceHistory;

    public Product() {
        super();
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl, Map<GregorianCalendar, BigDecimal> priceHistory) {
        super(id);
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.priceHistory = new PriceHistory();
        this.priceHistory.setPriceHistory(priceHistory);
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        super(id);
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public Product(String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) {
        super.setId(id);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPriceHistory(Map<GregorianCalendar, BigDecimal> priceHistory) {
        this.priceHistory.setPriceHistory(priceHistory);
    }

    public Map<GregorianCalendar, BigDecimal> getPriceHistory() {
        return priceHistory.getPriceHistory();
    }

    public boolean isValid() {
        return price != null && stock > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return stock == product.stock && Objects.equals(super.getId(), product.getId()) && Objects.equals(code, product.code) && Objects.equals(description, product.description) && Objects.equals(price, product.price) && Objects.equals(currency, product.currency) && Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash( code, description, price, currency, stock, imageUrl);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + super.getId() +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", currency=" + currency +
                ", stock=" + stock +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}