package com.es.phoneshop.model.cart;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public class DefaultCartService implements CartService {

    private ProductDao productDao;
    private static DefaultCartService defaultCartService;
    private static final String CART_SESSION_ATTRIBUTE=DefaultCartService.class.getName()+".cart";

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
    public synchronized Cart getCart(HttpServletRequest request) {
        Cart cart=(Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
        if(cart==null){
            request.getSession().setAttribute(CART_SESSION_ATTRIBUTE,new Cart());
        }
        return cart;
    }

    @Override
    public synchronized void add(Cart cart,Product product, int quantity) throws OutOfStockException {
        productDao.getProduct(product.getId());
        if (productDao.getProduct(product.getId()).getStock() < quantity) {
            throw new OutOfStockException("Not enough stock.");
        } else {
            CartItem cartItem = new CartItem(productDao.getProduct(product.getId()), quantity);
            cart.getItems().add(cartItem);
        }
    }
}
