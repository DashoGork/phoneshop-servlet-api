package com.es.phoneshop.service.cart;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CartServiceImplementation implements CartService {

    private ProductDao productDao;
    private static volatile CartServiceImplementation cartServiceImplementation;
    private static final String CART_SESSION_ATTRIBUTE = CartServiceImplementation.class.getName() + ".cart";

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();

    private CartServiceImplementation() {
        productDao = ArrayListProductDao.getArrayListProductDao();
    }

    public static CartServiceImplementation getDefaultCartService() {
        CartServiceImplementation result = cartServiceImplementation;
        if (result != null) {
            return result;
        }
        synchronized (CartServiceImplementation.class) {
            if (cartServiceImplementation == null) {
                cartServiceImplementation = new CartServiceImplementation();
            }
            return cartServiceImplementation;
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
