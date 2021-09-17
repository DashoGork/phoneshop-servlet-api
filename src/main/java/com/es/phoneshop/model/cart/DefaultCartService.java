package com.es.phoneshop.model.cart;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DefaultCartService implements CartService {

    private ProductDao productDao;
    private static volatile DefaultCartService defaultCartService;
    private static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();

    private DefaultCartService() {
        productDao = ArrayListProductDao.getArrayListProductDao();
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
    public Cart getCart(HttpServletRequest request) {
        readLock.lock();
        try {
            Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
            if (cart == null) {
                request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, new Cart());
            }
            return cart;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void add(Cart cart, Product product, int quantity) throws OutOfStockException {
        writeLock.lock();
        try {
            Product productFromDb = productDao.getProduct(product.getId());
            if (productFromDb.getStock() < quantity) {
                throw new OutOfStockException("Not enough stock.");
            } else {
                CartItem cartItem = new CartItem(productDao.getProduct(product.getId()), quantity);
                productFromDb.setStock(productFromDb.getStock() - quantity);
                cart.getItems().add(cartItem);
            }
        } finally {
            writeLock.unlock();
        }
    }
}
