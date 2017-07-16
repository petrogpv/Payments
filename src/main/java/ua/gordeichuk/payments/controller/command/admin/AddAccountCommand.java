package ua.gordeichuk.payments.controller.command.admin;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.service.AccountService;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDto;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddAccountCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(AddAccountCommand.class);
    private AccountService accountService;

    public AddAccountCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String userIdString = request.getParameter(Attribute.USER);
        Long userId = Long.parseLong(userIdString);
        Card card = accountService.addAccountAndCard(userId);
        writeMessageAndLog(request, card);
        return Path.MANAGE_USERS;
    }

    private void writeMessageAndLog(HttpServletRequest request, Card card) {
        MessageDto messageDto = new MessageDtoBuilder()
                .addMessage(Message.ACCOUNT_SUCCESSFULLY_CREATED)
                .addMessage(String.valueOf(card.getAccount().getId()))
                .addMessage(Message.AND_CARD_SUCCESSFULLY_CREATED)
                .addMessage(String.valueOf(card.getId()))
                .build();
        LOGGER.info(messageDto.getLogMessage());
        request.setAttribute(Attribute.MESSAGE, messageDto.getMessage());
    }
}
