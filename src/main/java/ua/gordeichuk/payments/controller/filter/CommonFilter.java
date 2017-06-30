package ua.gordeichuk.payments.controller.filter;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.util.LogMessage;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Администратор on 28.06.2017.
 */
public class CommonFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(CommonFilter.class);
    private static final String DEFAULT_PATH = "/";
    private static final String AUTH = "/auth";
    private static final String USER_ATTRIBUTE_NAME = "user";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getServletPath();
        HttpSession session = request.getSession(false);

        boolean test = pathNeedsAuthentication(path);
        boolean test2 = isUserSigned(session);

        if (pathNeedsAuthentication(path)) {
            if(!isUserSigned(session)) {
                LOGGER.info(LogMessage.ATTEMPT_TO_VISIT_AUTHORIZED);
                response.sendRedirect(DEFAULT_PATH);
            }
        } else {
            chain.doFilter(req, res);
        }
    }

    private boolean isUserSigned(HttpSession session) {
        return !(session == null || session.getAttribute(USER_ATTRIBUTE_NAME) == null);
    }

    private boolean pathNeedsAuthentication(String url) {
       return !(url.equals(DEFAULT_PATH) || url.startsWith(AUTH));
    }
    @Override
    public void destroy() {

    }
}
