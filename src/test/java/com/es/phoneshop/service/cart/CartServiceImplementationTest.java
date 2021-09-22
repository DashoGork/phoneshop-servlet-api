package com.es.phoneshop.service.cart;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.listener.DemoDataContextServletListener;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.cart.CartService;
import com.es.phoneshop.service.cart.CartServiceImplementation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceImplementationTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpSession session;
    @Mock
    ServletContextEvent servletContextEvent;

    @Mock
    ServletContext servletContext;

    private DemoDataContextServletListener demoDataContextServletListener;

    private CartService cartService;
    private Cart cart;
    private CartItem cartItem;
    private Product product;
    private Product productToAdd1;

    @Before
    public void setUp() throws Exception {
        demoDataContextServletListener = new DemoDataContextServletListener();
        servletContextEvent = new ServletContextEvent(servletContext);
        when(servletContextEvent.getServletContext().getInitParameter("initParamDemoDataListener")).thenReturn("true");
        demoDataContextServletListener.contextInitialized(servletContextEvent);
        cartService = CartServiceImplementation.getDefaultCartService();
        Currency usd = Currency.getInstance("USD");
        product = new Product("test", "test", new BigDecimal(150), usd, 40, "test");
        productToAdd1 = new Product(2l, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        cartItem = new CartItem(product, 1);
        when(request.getSession()).thenReturn(session);
        cart = new Cart();
        cart.setItem(cartItem);
    }

    @Test
    public void getNullCart() {
        when(session.getAttribute(any())).thenReturn(null);
        assertTrue(cartService.getCart(request) == null);
    }

    @Test
    public void getNotNullCart() {
        when(session.getAttribute(any())).thenReturn(cart);
        assertTrue(cartService.getCart(request).getItems().size() == 1);
    }

    @Test
    public void add() {
        cartService.add(cart, productToAdd1, 1);

        when(session.getAttribute(any())).thenReturn(cart);
        assertTrue(cartService.getCart(request).getItems().size() == 2);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void addOutOfStock() {
        expectedException.expect(OutOfStockException.class);
        cartService.add(cart, productToAdd1, 134);
    }
}