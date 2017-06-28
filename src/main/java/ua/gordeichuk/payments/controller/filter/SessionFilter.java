package ua.gordeichuk.payments.controller.filter;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Администратор on 28.06.2017.
 */
public class SessionFilter implements Filter {
    static final String DEFAULT_PATH = "/";
    static final String AUTH = "/auth";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);
        boolean auth = req.getServletPath().equals(AUTH);
        System.out.println("SP:" + req.getServletPath()+" Session : " + session + "Auth: " + auth);
        if (session == null && auth) {
            res.sendRedirect(DEFAULT_PATH);
        } else {
            filterChain.doFilter(req, res);
        }
    }


    @Override
    public void destroy() {

    }
}
