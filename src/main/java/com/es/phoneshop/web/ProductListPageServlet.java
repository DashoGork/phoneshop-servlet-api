package com.es.phoneshop.web;

import com.es.phoneshop.dao.product.impl.ArrayListProductDao;
import com.es.phoneshop.enums.ProductListPageParameters;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.viewHistory.ViewHistoryService;
import com.es.phoneshop.service.viewHistory.impl.ViewHistoryServiceImplementation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductListPageServlet extends HttpServlet {

    private ArrayListProductDao arrayListProductDao;
    private ViewHistoryService viewHistoryService;

    @Override
    public void init() throws ServletException {
        super.init();
        arrayListProductDao = ArrayListProductDao.getArrayListProductDao();
        viewHistoryService = ViewHistoryServiceImplementation.getViewHistoryServiceImplementation();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter(ProductListPageParameters.QUERY.name().toLowerCase());
        String sortField = request.getParameter(ProductListPageParameters.SORT.name().toLowerCase());
        String sortOrder = request.getParameter(ProductListPageParameters.ORDER.name().toLowerCase());
        request.setAttribute(ProductListPageParameters.VIEW_HISTORY.name().toLowerCase(), viewHistoryService.getViewHistory(request));
        List<Product> f=arrayListProductDao.findProducts(query, sortField, sortOrder);
        request.setAttribute(ProductListPageParameters.PRODUCTS.name().toLowerCase(), arrayListProductDao.findProducts(query, sortField, sortOrder));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
