package ua.gordeichuk.payments.controller.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.controller.Validator;
import ua.gordeichuk.payments.controller.exception.ServiceException;
import ua.gordeichuk.payments.controller.service.UserService;
import ua.gordeichuk.payments.controller.util.Attribute;
import ua.gordeichuk.payments.controller.util.LogMessage;
import ua.gordeichuk.payments.controller.util.Message;
import ua.gordeichuk.payments.controller.util.Page;
import ua.gordeichuk.payments.model.entity.User;
import ua.gordeichuk.payments.model.entity.UserAuth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 29.06.2017.
 */
public class SignInCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(SignInCommand.class);
    private UserService userService;

    public SignInCommand(UserService userService){
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        UserAuth userAuth = createUserAuthFromRequest(request);
        Validator.validateLogin(userAuth.getLogin());
        Validator.validatePassword(userAuth.getPassword());
        User user = userService.signInUser(userAuth);
        request.getSession().setAttribute(Attribute.USER, user);
        request.setAttribute(Attribute.MESSAGE, Message.getMessage(Message.SIGNED_IN_SUCCESS));

        LOGGER.info(LogMessage.USER_SIGNED_IN + user.getUserAuth().getLogin());
        return Page.INDEX;
    }

    private UserAuth createUserAuthFromRequest(HttpServletRequest request) {
        String login = request.getParameter(Attribute.USERNAME);
        String password = request.getParameter(Attribute.PASSWORD);
        return new UserAuth.Builder()
                .setLogin(login)
                .setPassword(password)
                .build();
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.VISITOR_WAS_DENIED_ACCESS + request.getParameter(Attribute.USERNAME));
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Page.INDEX;
    }
}
