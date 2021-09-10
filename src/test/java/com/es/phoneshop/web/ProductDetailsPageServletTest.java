package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.listener.DemoDataContextServletListener;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    ServletConfig config;
    @Mock
    ServletContextEvent servletContextEvent;
    private ProductDetailsPageServlet servlet;
    private ProductDao productDao;

    @Before
    public void setup() throws ServletException {
        servlet = new ProductDetailsPageServlet();
        productDao=ArrayListProductDao.getArrayListProductDao();
        Currency usd = Currency.getInstance("USD");
        Map<String, BigDecimal> priceHistory=new HashMap<>();
        priceHistory.put("10, Jan, 2019", BigDecimal.valueOf(600));
        priceHistory.put("10, Jan, 2020", BigDecimal.valueOf(500));
        productDao.save(new Product(1l, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg",priceHistory));
        servlet.init(config);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/0");
        when(request.getPathInfo()).thenReturn("/0");
       when(request.getRequestURI()).thenReturn("");
        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq("product"),any());
    }



}