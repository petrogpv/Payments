package ua.gordeichuk.payments.controller.command.auth;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.UserService;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDto;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;
import ua.gordeichuk.payments.util.Path;
import ua.gordeichuk.payments.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpActionCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(SignUpActionCommand.class);
    private UserService userService;
    private Validator validator = Validator.getInstance();

    public SignUpActionCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException {
        String login = request.getParameter(Attribute.USERNAME);
        String password = request.getParameter(Attribute.PASSWORD);
        String passwordConfirm = request.getParameter(Attribute.PASSWORD_CONFIRM);
        validator.validateLogin(login);
        validator.validatePasswordsOnSignUp(password, passwordConfirm);
        userService.signUpUser(login, password);
        writeMessageAndLog(request);
        return Path.DEFAULT;
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.REGISTRATION_ERROR
                + request.getParameter(Attribute.USERNAME));
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.SIGNUP;
    }
    private void writeMessageAndLog(HttpServletRequest request) {
        MessageDto messageDto = new MessageDtoBuilder()
                .addMessage(Message.SIGNED_UP_SUCCESSFULLY)
                .build();
        LOGGER.info(messageDto.getLogMessage());
        request.setAttribute(Attribute.MESSAGE, messageDto.getMessage());
    }
}
