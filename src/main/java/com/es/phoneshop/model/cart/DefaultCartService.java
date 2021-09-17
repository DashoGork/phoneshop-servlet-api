package com.es.phoneshop.model.cart;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

public class DefaultCartService implements CartService{

    private Cart cart;
    private ProductDao productDao;
    private static DefaultCartService defaultCartService;

    private DefaultCartService() {
        cart=new Cart();
        productDao=ArrayListProductDao.getArrayListProductDao();
    }

    public static DefaultCartService getDefaultCartService() {
        DefaultCartService result = defaultCartService;
        if (result != null) {
            return result;
        }
        synchronized (DefaultCartService.class) {
            if (defaultCartService == null) {
                defaultCartService = new DefaultCartService();
            }
            return defaultCartService;
        }
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void add(Product product, int quantity) {
        productDao.getProduct(product.getId());
        CartItem cartItem=new CartItem(productDao.getProduct(product.getId()),quantity);
        cart.getItems().add(cartItem);
    }
}
