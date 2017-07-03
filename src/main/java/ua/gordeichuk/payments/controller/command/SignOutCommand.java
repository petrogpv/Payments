package ua.gordeichuk.payments.controller.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.controller.util.Attribute;
import ua.gordeichuk.payments.controller.util.LogMessage;
import ua.gordeichuk.payments.controller.util.Page;
import ua.gordeichuk.payments.model.entity.User;

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
        User user = (User) session.getAttribute(Attribute.USER);
        LOGGER.info(LogMessage.USER_LOGOUT + user.getUserAuth().getLogin());
        session.invalidate();
        return Page.INDEX;
    }
}
