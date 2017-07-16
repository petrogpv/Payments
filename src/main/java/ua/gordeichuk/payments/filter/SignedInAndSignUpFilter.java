package ua.gordeichuk.payments.filter;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.util.LogMessage;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignedInAndSignUpFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(CommonVisitorFilter.class);
    private static final String DEFAULT_PATH = "/";
    private static final String USER_ATTRIBUTE_NAME = "user";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);

        if (isUserSigned(session)) {
            LOGGER.info(LogMessage.ATTEMPT_TO_SIGN_UP_BY_SIGNED_IN_USER);
            response.sendRedirect(DEFAULT_PATH);
        } else {
            chain.doFilter(req, res);
        }
    }

    private boolean isUserSigned(HttpSession session) {
        return !(session == null || session.getAttribute(USER_ATTRIBUTE_NAME) == null);
    }

    @Override
    public void destroy() {

    }
}
