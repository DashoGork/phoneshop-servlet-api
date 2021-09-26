package com.es.phoneshop.service.cart.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.cart.CartService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

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
                cart = new Cart();
                request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart);
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
                if (cart.getProducts().contains(productFromDb)) {
                    getCartItemFromCart(cart, productFromDb).addQuantity(quantity);
                } else {
                    CartItem cartItem = new CartItem(productFromDb, quantity);
                    productFromDb.setStock(productFromDb.getStock() - quantity);
                    cart.getItems().add(cartItem);
                }
                productFromDb.setStock(productFromDb.getStock() - quantity);
                recalculateCart(cart);
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void update(Cart cart, Product product, int quantity) throws OutOfStockException {
        writeLock.lock();
        try {
            Product productFromDb = productDao.getProduct(product.getId());
            if (productFromDb.getStock() < quantity) {
                throw new OutOfStockException("Not enough stock.");
            } else {
                CartItem itemFromCart = getCartItemFromCart(cart, productFromDb);
                if(itemFromCart!=null){
                    productFromDb.setStock(productFromDb.getStock() + itemFromCart.getQuantity() - quantity);
                    getCartItemFromCart(cart, productFromDb).setQuantity(quantity);
                    recalculateCart(cart);
                }else {
                    add(cart,productFromDb,quantity);
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void delete(Cart cart, Product product) {
        writeLock.lock();
        try {
            CartItem cartItem = getCartItemFromCart(cart, product);
            update(cart, product, -cartItem.getQuantity());
            int index = cart.getItems().indexOf(cartItem);
            cart.getItems().remove(index);
            recalculateCart(cart);
        } finally {
            writeLock.unlock();
        }
    }

    private void recalculateCart(Cart cart){
        cart.setTotalQuantity(cart.getItems().stream()
                .map(CartItem::getQuantity)
                .collect(Collectors.summingInt(item->item.intValue())));

        cart.setTotalPrice(cart.getItems().stream().
               map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO,(x,y)->x.add(y),BigDecimal::add)
        );

    }

    private CartItem getCartItemFromCart(Cart cart, Product product) {
        return cart.getItems().stream()
                .filter((cartItemFromStream -> product.getId().equals(cartItemFromStream.getProduct().getId()))).
                findFirst().orElse(null);
    }
}
