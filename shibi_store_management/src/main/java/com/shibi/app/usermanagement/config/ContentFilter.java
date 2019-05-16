package com.shibi.app.usermanagement.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by User on 25/06/2018.
 */
public class ContentFilter implements Filter {

    private static final Logger LOG =  LoggerFactory.getLogger(ContentFilter.class);


    @Override
    public void destroy(){}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        final String url = ((HttpServletRequest)request).getServletPath();

//        Authentication authentication = new UsernamePasswordAuthenticationToken()
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}
