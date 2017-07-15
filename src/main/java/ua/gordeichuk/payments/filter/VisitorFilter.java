package ua.gordeichuk.payments.filter;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.AppServlet;
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
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        if (session == null || !isUserAuthorized(session)) {
            LOGGER.info(LogMessage.ATTEMPT_TO_VISIT_WITHOUT_PERMISSION);
            response.sendRedirect(DEFAULT_PATH);
        } else {
            chain.doFilter(request, response);
        }
    }

    abstract boolean isUserAuthorized(HttpSession session);

    @Override
    public void destroy() {

    }
}