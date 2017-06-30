package ua.gordeichuk.payments.controller.filter;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.AppServlet;
import ua.gordeichuk.payments.util.LogMessage;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Администратор on 28.06.2017.
 */
public abstract  class VisitorFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AppServlet.class);
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

        if (session == null || !isUserEligible(session)) {
            LOGGER.info(LogMessage.ATTEMPT_TO_VISIT_WITHOUT_PERMISSION);
            res.sendRedirect(DEFAULT_PATH);
        } else {
            filterChain.doFilter(req, res);
        }
    }

    abstract boolean isUserEligible(HttpSession session);

    @Override
    public void destroy() {

    }
}