package ua.gordeichuk.payments.controller.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.service.CardService;
import ua.gordeichuk.payments.util.Page;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Администратор on 28.06.2017.
 */
public class DefaultCommand implements Command {
    public static final String USER_ATTRIBUTE = "user";
    private static final Logger LOGGER = Logger.getLogger(DefaultCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
            return needsAuthentication(request) ? Page.INDEX : Page.MAIN;
    }

    private boolean needsAuthentication (HttpServletRequest request){
        HttpSession session = request.getSession(false);
        return session == null ||  session.getAttribute(USER_ATTRIBUTE) == null;

    }
}
