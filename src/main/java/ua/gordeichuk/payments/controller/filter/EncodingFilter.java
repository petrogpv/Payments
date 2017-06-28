package ua.gordeichuk.payments.controller.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Администратор on 28.06.2017.
 */
public class EncodingFilter implements Filter {

    public static final String ENCODING = "utf-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}