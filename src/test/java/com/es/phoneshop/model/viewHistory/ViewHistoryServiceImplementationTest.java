package com.es.phoneshop.model.viewHistory;

import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewHistoryServiceImplementationTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpSession session;


    private ViewHistoryService viewHistory;
    private ViewHistory viewHistoryToTest;
    private Product product;
    private Product productToAdd1;
    private Product productToAdd2;
    private Product productToAdd3;

    @Before
    public void setup() {
        viewHistory = ViewHistoryServiceImplementation.getViewHistoryServiceImplementation();
        viewHistoryToTest = new ViewHistory();
        Currency usd = Currency.getInstance("USD");
        viewHistoryToTest.addProductInHistory(product);
        product = new Product("test", "test", new BigDecimal(150), usd, 40, "test");
        productToAdd1 = new Product(2l, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        productToAdd2 = new Product(3l, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        productToAdd3 = new Product(4l, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void getEmptyViewHistory() {
        when(session.getAttribute(any())).thenReturn(null);
        assertTrue(viewHistory.getViewHistory(request).getViewHistory().size() == 0);
    }

    @Test
    public void getNotEmptyViewHistory() {
        when(session.getAttribute(any())).thenReturn(viewHistoryToTest);
        assertTrue(viewHistory.getViewHistory(request).getViewHistory().size() == 1);
    }

    @Test
    public void add() {
        viewHistory.add(viewHistoryToTest, productToAdd1);
        when(session.getAttribute(any())).thenReturn(viewHistoryToTest);
        assertTrue(viewHistory.getViewHistory(request).getViewHistory().size() == 2);
        viewHistory.add(viewHistoryToTest, productToAdd2);
        assertTrue(viewHistory.getViewHistory(request).getViewHistory().size() == 3);
        viewHistory.add(viewHistoryToTest, productToAdd2);
        assertTrue(viewHistory.getViewHistory(request).getViewHistory().size() == 3);
        viewHistory.add(viewHistoryToTest, productToAdd3);
        assertTrue(viewHistory.getViewHistory(request).getViewHistory().size() == 3 & viewHistory.getViewHistory(request).getViewHistory().contains(productToAdd3));
        assertFalse(viewHistory.getViewHistory(request).getViewHistory().contains(product));
    }
}