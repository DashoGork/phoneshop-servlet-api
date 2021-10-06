package com.es.phoneshop.web;

import com.es.phoneshop.dao.order.OrderDao;
import com.es.phoneshop.dao.order.impl.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OverviewPageServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    OrderDao dao;
    @Mock
    ServletConfig config;

    private OverviewPageServlet servlet;
    private Order order;

    @Before
    public void setup() throws ServletException {
        order = new Order();
        servlet = new OverviewPageServlet();
        dao = ArrayListOrderDao.getArrayListProductDao();
        order.setSecureId("0");
        dao.save(order);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.init(config);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/0");
        when(request.getRequestURI()).thenReturn("");
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq("order"), any());
    }
}