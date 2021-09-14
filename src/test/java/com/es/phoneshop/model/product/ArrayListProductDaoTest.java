package com.es.phoneshop.model.product;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.listener.DemoDataContextServletListener;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {

    @Mock
    ServletContextEvent servletContextEvent;

    @Mock
    ServletContext servletContext;

    private DemoDataContextServletListener demoDataContextServletListener;
    private ProductDao productDao;
    private Currency usd;
    private Product testProduct;
    private Product updatedTestProduct;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getArrayListProductDao();
        demoDataContextServletListener = new DemoDataContextServletListener();
        servletContextEvent = new ServletContextEvent(servletContext);
        when(servletContextEvent.getServletContext().getInitParameter("initParamDemoDataListener")).thenReturn("true");
        demoDataContextServletListener.contextInitialized(servletContextEvent);
        testProduct = new Product("test", "test", new BigDecimal(150), usd, 40, "test");
        updatedTestProduct = new Product(13L, "test1", "test1", new BigDecimal(150), usd, 40, "test1");
        usd = Currency.getInstance("USD");
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void getProduct() {
        try {
            assertEquals(productDao.getProduct(0L),
                    new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        } catch (ProductNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findProducts() {
        assertEquals(productDao.findProducts().size(), 13);
    }

    @Test
    public void save() {
        productDao.save(testProduct);
        assertTrue(productDao.findProducts().contains(testProduct));
    }


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void delete() throws ProductNotFoundException {
        productDao.delete(0l);
        expectedException.expect(ProductNotFoundException.class);
        productDao.getProduct(0l);
    }

    @Test
    public void update() throws ProductNotFoundException {
        productDao.save(testProduct);
        productDao.update(updatedTestProduct);
        assertTrue(productDao.getProduct(13l).getCode().equals("test1"));
    }

    @Test
    public void findProductsWithString() {
        List<Product> actual = productDao.findProducts("Samsung II");
        assertTrue(actual.contains(new Product(1l, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg")));
    }

    @Test
    public void findProductsWithStringAndOrder() {
        Map<String, BigDecimal> priceHistory = new HashMap<>();
        priceHistory.put("10, Jan, 2019", BigDecimal.valueOf(600));
        priceHistory.put("10, Jan, 2020", BigDecimal.valueOf(500));

        List<Product> actual = productDao.findProducts("Samsung II", "price", "asc");
        assertTrue(actual.get(0).equals(new Product(2l, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg", priceHistory)));
    }
}
