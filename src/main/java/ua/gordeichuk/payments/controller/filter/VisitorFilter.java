package ua.gordeichuk.payments.controller.filter;

import ua.gordeichuk.payments.controller.Pages;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.enums.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Администратор on 28.06.2017.
 */
public abstract  class VisitorFilter implements Filter {

    static final String USER_ATTRIBUTE = "user";
    static final String DEFAULT_PATH = "/";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);

        if (session == null || !checkUser(session)) {
            res.sendRedirect(DEFAULT_PATH);
        } else {
            filterChain.doFilter(req, res);
        }
    }

    abstract boolean checkUser(HttpSession session);

    @Override
    public void destroy() {

    }
}