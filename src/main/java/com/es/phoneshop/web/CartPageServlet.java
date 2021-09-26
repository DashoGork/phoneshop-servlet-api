package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.CartParameters;
import com.es.phoneshop.enums.ProductDetailsPageParameters;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.service.cart.CartService;
import com.es.phoneshop.service.cart.impl.CartServiceImplementation;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getArrayListProductDao();
        cartService = CartServiceImplementation.getDefaultCartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(ProductDetailsPageParameters.CART.name().toLowerCase(), cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIdFromRequest = request.getParameterValues(CartParameters.PRODUCT_ID.name().toLowerCase());
        String[] quantityFromRequest = request.getParameterValues(CartParameters.QUANTITY.name().toLowerCase());
        Map<Long, String> errorMessage = new HashMap<>();
        for (int index = 0; index < productIdFromRequest.length; index++) {
            try {
                Long productId = Long.valueOf(productIdFromRequest[index]);
                int quantity = parseQuantity(request, quantityFromRequest[index]);
                cartService.update(cartService.getCart(request), productDao.getProduct(productId), quantity);
            } catch (NumberFormatException | ParseException e) {
                errorMessage.put(Long.valueOf(productIdFromRequest[index]), "Not a number.");
            } catch (OutOfStockException e) {
                errorMessage.put(Long.valueOf(productIdFromRequest[index]), e.getMessage());
            }
        }

        if (errorMessage.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart?message=Added to cart");
        } else {
            request.setAttribute(CartParameters.ERRORS.name().toLowerCase(), errorMessage);
            doGet(request, response);
        }
    }

    private int parseQuantity(HttpServletRequest request, String quantity) throws ParseException {
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        if (quantity.matches("\\d+")) {
            return format.parse(quantity)
                    .intValue();
        } else {
            throw new ParseException("Not a number", 0);
        }
    }
}
