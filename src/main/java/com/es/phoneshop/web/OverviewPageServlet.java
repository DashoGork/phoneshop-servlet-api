package com.es.phoneshop.web;

import com.es.phoneshop.enums.CheckoutPageParameters;
import com.es.phoneshop.service.order.OrderService;
import com.es.phoneshop.service.order.impl.OrderServiceImplementation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OverviewPageServlet extends HttpServlet {
    private OrderService orderService;


    @Override
    public void init() throws ServletException {
        super.init();
        orderService = OrderServiceImplementation.getOrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderSecureId = getId(request);

        request.setAttribute(CheckoutPageParameters.ORDER.name().toLowerCase()
                , orderService.getOrderBySecureId(orderSecureId));
        request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(request, response);
    }

    private String getId(HttpServletRequest request) {
        String productId = request.getPathInfo();
        return (productId.substring(1));
    }
}
