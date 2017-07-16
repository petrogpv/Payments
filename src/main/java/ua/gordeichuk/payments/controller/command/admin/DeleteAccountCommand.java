package ua.gordeichuk.payments.controller.command.admin;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.AccountService;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDto;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;
import ua.gordeichuk.payments.util.Path;
import ua.gordeichuk.payments.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAccountCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(AddAccountCommand.class);
    private Validator validator = Validator.getInstance();
    private AccountService accountService;

    public DeleteAccountCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String accountIdString = request.getParameter(Attribute.ACCOUNT);
        Long accountId = validator.validateAndParseAccountNumber(accountIdString);
        accountService.deleteAccount(accountId);
        writeMessageAndLog(request, accountId);
        return Path.MANAGE_USERS;
    }

    private void writeMessageAndLog(HttpServletRequest request, Long accountId) {
        MessageDto messageDto = new MessageDtoBuilder()
                .addMessage(Message.ACCOUNT_SUCCESSFULLY_DELETED)
                .addMessage(String.valueOf(accountId))
                .build();
        LOGGER.info(messageDto.getLogMessage());
        request.setAttribute(Attribute.MESSAGE, messageDto.getMessage());
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.ACCOUNT_DELETE_FAILED);
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.MANAGE_USERS;
    }
}
