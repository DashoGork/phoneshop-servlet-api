package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.ProductDetailsPageParameters;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.service.cart.CartService;
import com.es.phoneshop.service.cart.impl.CartServiceImplementation;
import com.es.phoneshop.service.viewHistory.ViewHistoryService;
import com.es.phoneshop.service.viewHistory.impl.ViewHistoryServiceImplementation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao;
    private CartService cartService;
    private ViewHistoryService viewHistoryService;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getArrayListProductDao();
        cartService = CartServiceImplementation.getDefaultCartService();
        viewHistoryService = ViewHistoryServiceImplementation.getViewHistoryServiceImplementation();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        viewHistoryService.add(viewHistoryService.getViewHistory(request), productDao.getProduct(getProductId(request)));
        request.setAttribute(ProductDetailsPageParameters.PRODUCT.name().toLowerCase(), productDao.getProduct(getProductId(request)));
        request.setAttribute(ProductDetailsPageParameters.CART.name().toLowerCase(), cartService.getCart(request));
        if (request.getRequestURI().contains(ProductDetailsPageParameters.PRICE_HISTORY.name().toLowerCase())) {
            request.getRequestDispatcher("/WEB-INF/pages/priceHistory.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message;
        try {
            NumberFormat format = NumberFormat.getInstance(request.getLocale());
            int quantity = format.parse(request.getParameter(ProductDetailsPageParameters.QUANTITY.name().toLowerCase()))
                    .intValue();
            cartService.add(cartService.getCart(request), productDao.getProduct(getProductId(request)), quantity);
            response.sendRedirect(request.getContextPath() + "/product/" + getProductId(request) + "?message=Added to cart successfully");
        } catch (NumberFormatException | ParseException e) {
            message = "Not a number";
            request.setAttribute(ProductDetailsPageParameters.ERROR.name().toLowerCase(),message);
            doGet(request, response);
        } catch (OutOfStockException e) {
            request.setAttribute(ProductDetailsPageParameters.ERROR.name().toLowerCase(), e.getMessage());
            doGet(request, response);
        }
    }

    private long getProductId(HttpServletRequest request) {
        String productId = request.getPathInfo();
        return Long.valueOf(productId.substring(1));
    }
}
