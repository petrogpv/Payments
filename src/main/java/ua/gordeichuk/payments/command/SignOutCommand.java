package ua.gordeichuk.payments.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.Command;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;
import ua.gordeichuk.payments.util.Page;
import ua.gordeichuk.payments.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Администратор on 30.06.2017.
 */
public class SignOutCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(SignOutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(Attribute.USER) != null) {
            invalidateSession(session);
        }
        return Page.INDEX;
    }
    private void invalidateSession(HttpSession session){
        User user = (User) session.getAttribute(Attribute.USER);
        LOGGER.info(LogMessage.USER_LOGOUT + user.getUserAuth().getLogin());
        session.invalidate();
    }
}
