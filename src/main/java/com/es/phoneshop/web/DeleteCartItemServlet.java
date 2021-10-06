package com.es.phoneshop.web;

import com.es.phoneshop.service.cart.CartService;
import com.es.phoneshop.service.cart.impl.CartServiceImplementation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = CartServiceImplementation.getDefaultCartService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long productId = getProductId(request);
        cartService.delete(cartService.getCart(request), productId);
        request.getRequestDispatcher("/cart").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private long getProductId(HttpServletRequest request) {
        String productId = request.getPathInfo();
        return Long.valueOf(productId.substring(1));
    }
}
