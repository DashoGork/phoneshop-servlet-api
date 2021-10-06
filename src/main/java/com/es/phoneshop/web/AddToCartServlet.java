package com.es.phoneshop.web;

import com.es.phoneshop.dao.product.ProductDao;
import com.es.phoneshop.dao.product.impl.ArrayListProductDao;
import com.es.phoneshop.enums.CartParameters;
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


public class AddToCartServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getArrayListProductDao();
        cartService = CartServiceImplementation.getDefaultCartService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] quantityFromRequest = request.getParameterValues(CartParameters.QUANTITY.name().toLowerCase());
        String productIdFromRequest = request.getParameter(CartParameters.PRODUCT_ID.name().toLowerCase());
        String errorMessage = null;
        try {
            Long productId = Long.valueOf(productIdFromRequest);
            int quantity = parseQuantity(request, quantityFromRequest[productId.intValue()]);
            cartService.update(cartService.getCart(request), productId, quantity);
        } catch (NumberFormatException | ParseException e) {
            errorMessage = "Not a number.";
        } catch (OutOfStockException e) {
            errorMessage = e.getMessage();
        }


        if (errorMessage == null) {
            request.setAttribute("success", "Added successfully.");
            response.sendRedirect(request.getContextPath() + "/products");
        } else {
            request.setAttribute(CartParameters.ERRORS.name().toLowerCase(), errorMessage);
            request.getRequestDispatcher("/products").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
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
