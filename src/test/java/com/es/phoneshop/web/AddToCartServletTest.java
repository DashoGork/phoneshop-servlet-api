package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.CartParameters;
import com.es.phoneshop.listener.DemoDataContextServletListener;
import com.es.phoneshop.model.cart.Cart;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddToCartServletTest {

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

    private AddToCartServlet servlet;
    private ProductDao productDao;
    private DemoDataContextServletListener demoDataContextServletListener;


    @Before
    public void setUp() throws Exception {
        servlet = new AddToCartServlet();

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
    public void testDoPost() throws ServletException, IOException {
        when(request.getLocale()).thenReturn(new Locale("ENG"));
        when(request.getParameterValues(CartParameters.QUANTITY.name().toLowerCase())).thenReturn(new String[]{"0", "1"});
        when(request.getParameter(any())).thenReturn("0");
        servlet.doPost(request, response);
        verify(response).sendRedirect(any());
    }
}