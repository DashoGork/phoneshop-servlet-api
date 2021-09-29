package com.es.phoneshop.listener;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.*;

public class DemoDataContextServletListener implements ServletContextListener {

    private ProductDao productDao;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        boolean init = Boolean.valueOf(servletContextEvent.getServletContext().getInitParameter("initParamDemoDataListener"));
        if (init) {
            productDao = ArrayListProductDao.getArrayListProductDao();
            defaultProductList().stream().forEach((product -> productDao.save(product)));
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


    private List<Product> defaultProductList() {
        List<Product> defaultProductList = new ArrayList<>();
        Map<GregorianCalendar, BigDecimal> priceHistory = new HashMap<>();
        priceHistory.put(new GregorianCalendar(2017, 10, 20), BigDecimal.valueOf(600));
        priceHistory.put(new GregorianCalendar(2019, 10, 22), BigDecimal.valueOf(500));
        Currency usd = Currency.getInstance("USD");
        defaultProductList.add(new Product(0l, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", priceHistory));
        defaultProductList.add(new Product(1l, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg", priceHistory));
        defaultProductList.add(new Product(2l, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg", priceHistory));
        defaultProductList.add(new Product(3l, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg", priceHistory));
        defaultProductList.add(new Product(4l, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg", priceHistory));
        defaultProductList.add(new Product(5l, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg", priceHistory));
        defaultProductList.add(new Product(6l, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg", priceHistory));
        defaultProductList.add(new Product(7l, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg", priceHistory));
        defaultProductList.add(new Product(8l, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg", priceHistory));
        defaultProductList.add(new Product(9l, "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg", priceHistory));
        defaultProductList.add(new Product(10l, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg", priceHistory));
        defaultProductList.add(new Product(11l, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg", priceHistory));
        defaultProductList.add(new Product(12l, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg", priceHistory));
        return defaultProductList;
    }
}
