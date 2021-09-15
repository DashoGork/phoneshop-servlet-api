package com.es.phoneshop.web;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.ProductListPageParameters;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {

    private ArrayListProductDao arrayListProductDao;

    @Override
    public void init() throws ServletException {
        super.init();
        arrayListProductDao = ArrayListProductDao.getArrayListProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter(ProductListPageParameters.query.name());
        String sortField = request.getParameter(ProductListPageParameters.sort.name());
        String sortOrder = request.getParameter(ProductListPageParameters.order.name());

        if (query != null) {
            if (sortField != null && sortOrder != null) {
                request.setAttribute("products", arrayListProductDao.findProducts(query, sortField, sortOrder));
            } else {
                request.setAttribute("products", arrayListProductDao.findProducts(query));
            }
        } else {
            request.setAttribute("products", arrayListProductDao.findProducts());
        }request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
