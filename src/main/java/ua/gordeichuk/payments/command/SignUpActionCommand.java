package ua.gordeichuk.payments.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.Command;
import ua.gordeichuk.payments.Validator;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.UserService;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;
import ua.gordeichuk.payments.util.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 05.07.2017.
 */
public class SignUpActionCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(SignUpActionCommand.class);
    private UserService userService;
    private Validator validator = Validator.getInstance();
    public SignUpActionCommand(UserService userService) {
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
//        TODO make DTO?
        String login =  request.getParameter(Attribute.USERNAME);
        String password =  request.getParameter(Attribute.PASSWORD);
        String passwordConfirm =  request.getParameter(Attribute.PASSWORD_CONFIRM);

        validator.validateLogin(login);
        validator.validatePasswordsOnSignUp(password, passwordConfirm);

        userService.signUpUser(login, password, passwordConfirm);
//        request.getSession().setAttribute(Attribute.USER, user);
        return Path.DEFAULT;
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.REGISTRATION_ERROR + request.getParameter(Attribute.USERNAME));
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.SIGNUP;
    }
}
