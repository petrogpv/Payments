package ua.gordeichuk.payments.controller.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.controller.exception.ServiceException;
import ua.gordeichuk.payments.controller.service.UserService;
import ua.gordeichuk.payments.controller.util.Attribute;
import ua.gordeichuk.payments.controller.util.LogMessage;
import ua.gordeichuk.payments.controller.util.Page;
import ua.gordeichuk.payments.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 30.06.2017.
 */
public class SignUpCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(SignUpCommand.class);
    private UserService userService;

    public SignUpCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String login =  request.getParameter(Attribute.USERNAME);
        String password =  request.getParameter(Attribute.PASSWORD);
        String passwordConfirm =  request.getParameter(Attribute.PASSWORD_CONFIRM);
        User user = userService.signUpUser(login, password, passwordConfirm);
//        request.getSession().setAttribute(Attribute.USER, user);
        return Page.MAIN;
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.REGISTRATION_ERROR + request.getParameter(Attribute.USERNAME));
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Page.SIGN_UP;
    }
}
