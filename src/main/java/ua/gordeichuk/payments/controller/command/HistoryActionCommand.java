package ua.gordeichuk.payments.controller.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.util.Validator;
import ua.gordeichuk.payments.controller.parameters.dto.HistoryParamDto;
import ua.gordeichuk.payments.dao.parameters.dto.TransactionParamDto;
import ua.gordeichuk.payments.entity.Transaction;
import ua.gordeichuk.payments.entity.enums.TransactionType;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.TransactionService;
import ua.gordeichuk.payments.service.enums.SortType;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDto;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;
import ua.gordeichuk.payments.util.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class HistoryActionCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(HistoryActionCommand.class);
    private TransactionService transactionService;
    private Validator validator = Validator.getInstance();

    public HistoryActionCommand(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException {

        HistoryParamDto historyParamDto = extractHistoryParams(request);
        TransactionParamDto paramDto = validateAndGetParams(historyParamDto);
        List<Transaction> transactions = transactionService.findTransactionsByParamDto(paramDto);

        if (transactions.isEmpty()) {
            writeNotFoundMessages(request);

        } else {
            int foundTransactions = transactions.size();
            LOGGER.info(LogMessage.TRANSACTIONS_FOUND + foundTransactions);
            request.setAttribute(Attribute.TRANSACTIONS, transactions);
        }
        return Path.HISTORY;
    }

    private void writeNotFoundMessages(HttpServletRequest request) {
        MessageDto messageDto = new MessageDtoBuilder()
                .addMessage(Message.TRANSACTIONS_NOT_FOUND)
                .build();
        request.setAttribute(Attribute.MESSAGE_ERROR, messageDto.getMessage());
        LOGGER.info(messageDto.getLogMessage());
    }

    private HistoryParamDto extractHistoryParams(HttpServletRequest request) {
        return new HistoryParamDto.Builder()
                .setCardIdString(request.getParameter(Attribute.CARD))
                .setDateFromString(request.getParameter(Attribute.DATE_FROM))
                .setDateToString(request.getParameter(Attribute.DATE_TO))
                .setTransactionTypeString(request.getParameter(Attribute.TRANSACTION_TYPE))
                .setSortTypeString(request.getParameter(Attribute.SORT_TYPE))
                .build();

    }

    private TransactionParamDto validateAndGetParams(HistoryParamDto historyParamDto)
            throws ServiceException {
        Long cardId = validator.validateAndParseCardNumber(historyParamDto.getCardIdString());
        Date dateFrom = validator.validateAndParseDate(historyParamDto.getDateFromString());
        Date dateTo = validator.validateAndParseDate(historyParamDto.getDateToString());
        validator.validateDates(dateFrom, dateTo);
        SortType sortType = validator.validateSortType(historyParamDto.getSortTypeString());
        TransactionType transactionType = validator
                .validateTransactionType(historyParamDto.getTransactionTypeString());
        return new TransactionParamDto.Builder()
                .setCardId(cardId)
                .setDateFrom(dateFrom)
                .setDateTo(dateTo)
                .setSortType(sortType)
                .setTransactionType(transactionType)
                .build();
    }


    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.SEARCHING_TRANSACTIONS_FAILED);
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.HISTORY;
    }

}
