package ua.gordeichuk.payments.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.Command;
import ua.gordeichuk.payments.Validator;
import ua.gordeichuk.payments.entity.Account;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.enums.CardStatus;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.CardService;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDto;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;
import ua.gordeichuk.payments.util.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Валерий on 13.07.2017.
 */
public class AddCardCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(AddCardCommand.class);
    private Validator validator = Validator.getInstance();
    private CardService cardService;

    public AddCardCommand(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Card card = extractCardFromRequest(request);
        cardService.addCard(card);
        writeMessageAndLog(request, card);
        return Path.MANAGE_USERS;
    }

    private Card extractCardFromRequest(HttpServletRequest request) throws ServiceException {
        String userIdString = request.getParameter(Attribute.USER);
        String accountIdString = request.getParameter(Attribute.ACCOUNT);
        Long userId =  Long.parseLong(userIdString);
        Long accountId = validator.validateAndParseAccountNumber(accountIdString);
        User user = new User();
        user.setId(userId);
        Account account = new Account();
        account.setId(accountId);
        Card card = new Card.Builder()
                .setUser(user)
                .setAccount(account)
                .setCardStatus(CardStatus.ACTIVE)
                .build();
        return  card;
    }
    private void writeMessageAndLog(HttpServletRequest request, Card card){
        MessageDto messageDto = new MessageDtoBuilder()
                .addMessage(Message.CARD_SUCCESSFULLY_CREATED)
                .addMessage(String .valueOf(card.getId()))
                .build();
        LOGGER.info(messageDto.getLogMessage());
        request.setAttribute(Attribute.MESSAGE, messageDto.getMessage());
    }
    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.ADD_CARD_FAILED);
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.MANAGE_USERS;
    }
}
