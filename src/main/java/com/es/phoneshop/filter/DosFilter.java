package com.es.phoneshop.filter;

import com.es.phoneshop.service.filter.DosFilterService;
import com.es.phoneshop.service.filter.impl.DosFilterServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {

    private DosFilterService service;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        service=DosFilterServiceImpl.getDefaultDosFilterService();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(service.isAllowed(servletRequest.getRemoteAddr())){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            ((HttpServletResponse)servletResponse).setStatus(429);
        }
    }

    @Override
    public void destroy() {

    }
}
