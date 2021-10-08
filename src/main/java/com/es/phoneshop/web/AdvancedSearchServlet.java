package com.es.phoneshop.web;

import com.es.phoneshop.dao.product.impl.ArrayListProductDao;
import com.es.phoneshop.enums.*;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.viewHistory.ViewHistoryService;
import com.es.phoneshop.service.viewHistory.impl.ViewHistoryServiceImplementation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;

public class AdvancedSearchServlet extends HttpServlet {

    private ArrayListProductDao arrayListProductDao;

    @Override
    public void init() throws ServletException {
        super.init();
        arrayListProductDao = ArrayListProductDao.getArrayListProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(AdvancedSearchPageParameters.MATCHING_METHOD.name().toLowerCase(), Arrays.asList(SortMatching.values()));
        request.getRequestDispatcher("/WEB-INF/pages/advansedSearch.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errorMessage = new HashMap<>();
        String query = request.getParameter(AdvancedSearchPageParameters.QUERY.name().toLowerCase());
        BigDecimal maxPrice = setRequestedParameter(request, AdvancedSearchPageParameters.MAX_PRICE.name().toLowerCase(),
                errorMessage);
        BigDecimal minPrice = setRequestedParameter(request, AdvancedSearchPageParameters.MIN_PRICE.name().toLowerCase(),
                errorMessage);
        SortMatching matching = setMatching(request, errorMessage);
        if (errorMessage.isEmpty()) {
            request.setAttribute(AdvancedSearchPageParameters.PRODUCTS.name().toLowerCase()
                    , arrayListProductDao.findProducts(query, minPrice, maxPrice, matching));
        } else {
            request.setAttribute(CartParameters.ERRORS.name().toLowerCase(), errorMessage);
        }
        doGet(request, response);
    }

    private BigDecimal setRequestedParameter(HttpServletRequest request, String parameter,
                                             Map<String, String> errors) {
        String value = request.getParameter(parameter);

        try {
            if (value.isEmpty() || value == null) {
                return new BigDecimal(-1);
            }
            return BigDecimal.valueOf(Long.valueOf(value));
        } catch (NumberFormatException e) {
            errors.put(parameter, parameter + " is wrong");
        }
        return new BigDecimal(-1);
    }

    private SortMatching setMatching(HttpServletRequest request,
                                     Map<String, String> errors) {
        String parameter = AdvancedSearchPageParameters.MATCHING_METHOD.name().toLowerCase();
        String value = request.getParameter(parameter).toUpperCase(Locale.ROOT);
        if (value.isEmpty() || value == null) {
            errors.put(parameter, parameter + " is null");
        } else {
            return (SortMatching.valueOf(value));
        }
        return (SortMatching.valueOf(value));
    }
}