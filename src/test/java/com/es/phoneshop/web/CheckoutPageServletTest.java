package com.es.phoneshop.web;

import com.es.phoneshop.enums.CartParameters;
import com.es.phoneshop.enums.CheckoutPageParameters;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.cart.CartService;
import com.es.phoneshop.service.cart.impl.CartServiceImplementation;
import com.es.phoneshop.service.order.OrderService;
import com.es.phoneshop.service.order.impl.OrderServiceImplementation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {

    @Mock
    OrderService orderService;
    @Mock
    CartService cartService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;
    @Mock
    private OrderServiceImplementation orderServiceImplementation;
    @Mock
    HttpSession session;


    private CheckoutPageServlet servlet;

    @Before
    public void setUp() throws Exception {
        servlet = new CheckoutPageServlet();

        servlet.init(config);
        when(request.getSession()).thenReturn(session);
//        when(session.getAttribute(any())).thenReturn(new Cart());
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        orderService = OrderServiceImplementation.getOrderService();
        cartService = CartServiceImplementation.getDefaultCartService();
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(56));
        Cart cart = new Cart();
        cart.setItem(new CartItem(product, 1));
        cart.setTotalPrice(BigDecimal.valueOf(56));
        cart.setTotalQuantity(1);
        when(request.getSession().getAttribute(any())).thenReturn(cart);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq(CheckoutPageParameters.ORDER.name().toLowerCase()), any());
    }

    @Test
    public void doPost() throws IOException, ServletException {
        when(request.getParameter(CheckoutPageParameters.FIRST_NAME.name().toLowerCase())).thenReturn("fff");
        when(request.getParameter(CheckoutPageParameters.LAST_NAME.name().toLowerCase())).thenReturn("fff");
        when(request.getParameter(CheckoutPageParameters.ADDRESS.name().toLowerCase())).thenReturn("fff");
        when(request.getParameter(CheckoutPageParameters.PAYMENT_METHOD.name().toLowerCase())).thenReturn("cart");
        when(request.getParameter(CheckoutPageParameters.DELIVERY_DATE.name().toLowerCase())).thenReturn("11.11.11");
        servlet.doPost(request, response);
        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq(CartParameters.ERRORS.name().toLowerCase()), any());
    }
}