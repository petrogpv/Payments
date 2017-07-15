package ua.gordeichuk.payments.filter;

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
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding(ENCODING);
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}