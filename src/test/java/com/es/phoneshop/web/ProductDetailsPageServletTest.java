package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.ProductDetailsPageParameters;
import com.es.phoneshop.listener.DemoDataContextServletListener;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    HttpSession session;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;
    @Mock
    private ServletContext servletContext;
    @Mock
    private ServletContextEvent servletContextEvent;
    @Mock
    CartService cartService;

    private ProductDetailsPageServlet servlet;
    private ProductDao productDao;
    private DemoDataContextServletListener demoDataContextServletListener;

    @Before
    public void setup() throws ServletException {
        servlet = new ProductDetailsPageServlet();
        productDao = ArrayListProductDao.getArrayListProductDao();
        demoDataContextServletListener = new DemoDataContextServletListener();
        servletContextEvent = new ServletContextEvent(servletContext);
        when(servletContextEvent.getServletContext().getInitParameter("initParamDemoDataListener")).thenReturn("true");
        demoDataContextServletListener.contextInitialized(servletContextEvent);
        servlet.init(config);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/0");
        when(request.getRequestURI()).thenReturn("");
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq("product"), any());
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getLocale()).thenReturn(Locale.ROOT);
        when(request.getPathInfo()).thenReturn("/0");
        when(request.getParameter(ProductDetailsPageParameters.QUANTITY.name().toLowerCase())).thenReturn("1");
        when(request.getRequestURI()).thenReturn("");
        when(cartService.getCart(request)).thenReturn(new Cart());
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(any())).thenReturn(new Cart());
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }
}