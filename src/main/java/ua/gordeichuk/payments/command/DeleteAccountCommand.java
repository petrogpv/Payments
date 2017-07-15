package ua.gordeichuk.payments.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.Command;
import ua.gordeichuk.payments.service.localization.SessionLocale;
import ua.gordeichuk.payments.Validator;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.AccountService;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDto;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;
import ua.gordeichuk.payments.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

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
        String sessionId = request.getSession().getId();
        Locale locale = SessionLocale.getInstance().getLocale(sessionId);
        accountService.deleteAccount(accountId);
        writeMessageAndLog(request, accountId);
        return Path.MANAGE_USERS;
    }
    private void writeMessageAndLog(HttpServletRequest request, Long accountId){
        String sessionId = request.getSession().getId();
        Locale locale = SessionLocale.getInstance().getLocale(sessionId);
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
