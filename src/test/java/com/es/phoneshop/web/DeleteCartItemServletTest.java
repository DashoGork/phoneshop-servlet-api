package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.listener.DemoDataContextServletListener;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.cart.impl.CartServiceImplementation;
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
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteCartItemServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;
    @Mock
    private CartServiceImplementation cartServiceImplementation;
    @Mock
    HttpSession session;
    @Mock
    private ServletContext servletContext;
    @Mock
    private ServletContextEvent servletContextEvent;

    private DeleteCartItemServlet servlet;
    private ProductDao productDao;
    private DemoDataContextServletListener demoDataContextServletListener;
    private Cart cart;
    private CartItem cartItem;

    @Before
    public void setUp() throws Exception {
        servlet = new DeleteCartItemServlet();
        cart =new Cart();
        cartItem=new CartItem(new Product(0l, "sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"),1);
        cart.setItem(cartItem);
        servlet.init(config);
        when(request.getSession()).thenReturn(session);
        //when(session.getAttribute(any())).thenReturn(cart);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);

        productDao = ArrayListProductDao.getArrayListProductDao();
        demoDataContextServletListener = new DemoDataContextServletListener();
        servletContextEvent = new ServletContextEvent(servletContext);
        when(servletContextEvent.getServletContext().getInitParameter("initParamDemoDataListener")).thenReturn("true");
        demoDataContextServletListener.contextInitialized(servletContextEvent);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(session.getAttribute(any())).thenReturn(cart);
        when(request.getPathInfo()).thenReturn("/0");

        servlet.doPost(request,response);
        verify(requestDispatcher).forward(request, response);
    }
}