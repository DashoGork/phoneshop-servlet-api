package com.es.phoneshop.web;

import com.es.phoneshop.enums.ProductDetailsPageParameters;
import com.es.phoneshop.service.cart.CartService;
import com.es.phoneshop.service.cart.impl.CartServiceImplementation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MiniCartServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = CartServiceImplementation.getDefaultCartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(ProductDetailsPageParameters.CART.name().toLowerCase(), cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/miniCart.jsp").include(request, response);
    }
}
