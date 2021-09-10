package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.ProductNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao= ArrayListProductDao.getArrayListProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId=request.getPathInfo();
        System.out.println(productId);
        try {
            request.setAttribute("product",productDao.getProduct(Long.valueOf(productId.substring(1))));
        } catch (ProductNotFoundException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);

    }
}
