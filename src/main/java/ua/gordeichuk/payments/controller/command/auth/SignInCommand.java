package ua.gordeichuk.payments.controller.command.auth;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.util.Validator;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.UserAuth;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.UserService;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;
import ua.gordeichuk.payments.util.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(SignInCommand.class);
    private UserService userService;
    private Validator validator = Validator.getInstance();

    public SignInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException {
        UserAuth userAuth = extractUserAuthFromRequest(request);
        validateUser(userAuth);
        User user = userService.signInUser(userAuth);
        request.getSession().setAttribute(Attribute.USER, user);
        request.setAttribute(Attribute.MESSAGE, new MessageDtoBuilder()
                .getMessage(Message.SIGNED_IN_SUCCESS));

        LOGGER.info(LogMessage.USER_SIGNED_IN + user.getUserAuth().getLogin());
        return Path.DEFAULT;
    }

    private UserAuth extractUserAuthFromRequest(HttpServletRequest request) {
        String login = request.getParameter(Attribute.USERNAME);
        String password = request.getParameter(Attribute.PASSWORD);
        UserAuth userAuth = new UserAuth();
        userAuth.setLogin(login);
        userAuth.setPassword(password);
        return userAuth;
    }

    private void validateUser(UserAuth userAuth) throws ServiceException {
        validator.validateLogin(userAuth.getLogin());
        validator.validatePassword(userAuth.getPassword());
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.VISITOR_WAS_DENIED_ACCESS
                + request.getParameter(Attribute.USERNAME));
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.DEFAULT;
    }
}
