package com.es.phoneshop.dao.product;

import com.es.phoneshop.model.product.Product;

import java.security.InvalidParameterException;
import java.util.List;

public interface ProductDao {
    Product getItem(Long id);
    List<Product> findProducts(String name,String sortField, String sortOrder) throws InvalidParameterException;
    List<Product> findProducts(String name) throws InvalidParameterException;
    List<Product> findProducts();
    void delete(Long id);
    void saveItem(Product product);
    void update(Product product);
}
