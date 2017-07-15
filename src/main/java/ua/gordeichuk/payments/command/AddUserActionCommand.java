package ua.gordeichuk.payments.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.Command;
import ua.gordeichuk.payments.Validator;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.UserAuth;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.UserService;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDto;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;
import ua.gordeichuk.payments.util.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUserActionCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(AddUserActionCommand.class);
    private UserService userService;
    private Validator validator = Validator.getInstance();

    public AddUserActionCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        User user = extractUserFromRequest(request);
        String sessionId = request.getSession().getId();
        userService.addUser(user);
        writeMessage(request);
        return Path.ADD_USER;
    }

    private void writeMessage(HttpServletRequest request) {
        MessageDto messageDto = new MessageDtoBuilder()
                .addMessage(Message.USER_SUCCESSFULLY_REGISTERED)
                .build();
        request.setAttribute(Attribute.MESSAGE, messageDto.getMessage());
        LOGGER.info(messageDto.getLogMessage());
    }

    private User extractUserFromRequest(HttpServletRequest request) throws ServiceException {
        String firstName = request.getParameter(Attribute.FIRST_NAME);
        String lastName = request.getParameter(Attribute.LAST_NAME);
        String login = request.getParameter(Attribute.LOGIN);
        validator.validateFirstName(firstName);
        validator.validateLastName(lastName);
        validator.validateLogin(login);
        UserAuth userAuth = new UserAuth();
        userAuth.setLogin(login);
        return new User.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setUserAuth(userAuth)
                .build();
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.USER_REGISTRATION_ERROR + request.getParameter(Attribute.LOGIN));
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.ADD_USER;
    }
}
