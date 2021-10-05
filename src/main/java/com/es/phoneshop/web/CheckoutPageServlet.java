package com.es.phoneshop.web;

import com.es.phoneshop.enums.CartParameters;
import com.es.phoneshop.enums.CheckoutPageParameters;
import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.enums.ProductDetailsPageParameters;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.cart.CartService;
import com.es.phoneshop.service.cart.impl.CartServiceImplementation;
import com.es.phoneshop.service.order.OrderService;
import com.es.phoneshop.service.order.impl.OrderServiceImplementation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;


    @Override
    public void init() throws ServletException {
        super.init();
        cartService = CartServiceImplementation.getDefaultCartService();
        orderService = OrderServiceImplementation.getOrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(CheckoutPageParameters.ORDER.name().toLowerCase()
                , orderService.create(cartService.getCart(request)));
        request.setAttribute(CheckoutPageParameters.PAYMENT_METHOD.name().toLowerCase(), orderService.getPaymentMethods());
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.create(cart);
        Map<String, String> errorMessage = new HashMap<>();
        setRequestedParameter(request, CheckoutPageParameters.FIRST_NAME.name().toLowerCase(),
                errorMessage, order::setFirstName);
        setRequestedParameter(request, CheckoutPageParameters.LAST_NAME.name().toLowerCase(),
                errorMessage, order::setLastName);
        setRequestedParameter(request, CheckoutPageParameters.ADDRESS.name().toLowerCase(),
                errorMessage, order::setAddress);
        setPaymentMethod(request, order, errorMessage);
        setDateMethod(request, order, errorMessage);

        if (errorMessage.isEmpty()) {
            request.getSession().removeAttribute(ProductDetailsPageParameters.CART.name().toLowerCase());
            orderService.placeOrder(order);
            response.sendRedirect(request.getContextPath() + "/order/overview/" + order.getSecureId());
        } else {
            request.setAttribute(CartParameters.ERRORS.name().toLowerCase(), errorMessage);
            doGet(request, response);
        }
    }

    private void setRequestedParameter(HttpServletRequest request, String parameter,
                                       Map<String, String> errors, Consumer<String> consumer) {
        String value = request.getParameter(parameter);
        if (value.isEmpty() || value == null) {
            errors.put(parameter, parameter + " is null");
        } else {
            consumer.accept(value);
        }
    }

    private void setPaymentMethod(HttpServletRequest request, Order order,
                                  Map<String, String> errors) {
        String parameter = CheckoutPageParameters.PAYMENT_METHOD.name().toLowerCase();
        String value = request.getParameter(parameter).toUpperCase(Locale.ROOT);
        if (value.isEmpty() || value == null) {
            errors.put(parameter, parameter + " is null");
        } else {
            order.setPaymentMethod(PaymentMethod.valueOf(value));
        }
    }

    private void setDateMethod(HttpServletRequest request, Order order,
                               Map<String, String> errors) {
        String parameter = CheckoutPageParameters.DELIVERY_DATE.name().toLowerCase();
        String value = request.getParameter(parameter);
        try {
            if (value.isEmpty() || value == null) {
                errors.put(parameter, parameter + " is null");
            } else {
                SimpleDateFormat availDate = new SimpleDateFormat("dd-MM-yyyy");
                Date chosenDate = availDate.parse(value);
                order.setDeliveryDate(chosenDate);
            }
        } catch (ParseException e) {
            errors.put(parameter, parameter + " is invalid");
        }
    }
}
