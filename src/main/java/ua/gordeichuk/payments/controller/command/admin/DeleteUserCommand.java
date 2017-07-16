package ua.gordeichuk.payments.controller.command.admin;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(DeleteUserCommand.class);
    private UserService userService;

    public DeleteUserCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String userIdString = request.getParameter(Attribute.USER);
        Long userId = Long.parseLong(userIdString);
        userService.deleteUser(userId);
        writeMessageAndLog(request, userId);
        return Path.MANAGE_USERS;
    }

    private void writeMessageAndLog(HttpServletRequest request, Long userId) {
        MessageDto messageDto = new MessageDtoBuilder()
                .addMessage(Message.USER_SUCCESSFULLY_DELETED)
                .addMessage(String.valueOf(userId))
                .build();
        LOGGER.info(messageDto.getLogMessage());
        request.setAttribute(Attribute.MESSAGE, messageDto.getMessage());
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.USER_DELETION_FAILED);
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.MANAGE_USERS;
    }
}
