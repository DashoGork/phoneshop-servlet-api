package com.es.phoneshop.web;

import com.es.phoneshop.dao.product.ProductDao;
import com.es.phoneshop.dao.product.impl.ArrayListProductDao;
import com.es.phoneshop.enums.CartParameters;
import com.es.phoneshop.enums.ProductDetailsPageParameters;
import com.es.phoneshop.listener.DemoDataContextServletListener;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.cart.CartService;
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
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {

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
    private CartServiceImplementation cartServiceImplementation;
    @Mock
    HttpSession session;
    @Mock
    private ServletContext servletContext;
    @Mock
    private ServletContextEvent servletContextEvent;

    private CartPageServlet servlet;
    private ProductDao productDao;
    private DemoDataContextServletListener demoDataContextServletListener;


    @Before
    public void setUp() throws Exception {
        servlet=new CartPageServlet();

        servlet.init(config);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(any())).thenReturn(new Cart());
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);

        productDao = ArrayListProductDao.getArrayListProductDao();
        demoDataContextServletListener = new DemoDataContextServletListener();
        servletContextEvent = new ServletContextEvent(servletContext);
        when(servletContextEvent.getServletContext().getInitParameter("initParamDemoDataListener")).thenReturn("true");
        demoDataContextServletListener.contextInitialized(servletContextEvent);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq(ProductDetailsPageParameters.CART.name().toLowerCase()), any());
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getLocale()).thenReturn(new Locale("ENG"));
        when(request.getParameterValues(CartParameters.PRODUCT_ID.name().toLowerCase())).thenReturn(new String[]{"0"});
        when(request.getParameterValues(CartParameters.QUANTITY.name().toLowerCase())).thenReturn(new String[]{"1"});

        servlet.doPost(request, response);
        verify(response).sendRedirect(any());
    }
}