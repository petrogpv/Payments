package ua.gordeichuk.payments.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.Command;
import ua.gordeichuk.payments.Validator;

import ua.gordeichuk.payments.dto.entityparam.TransactionParamDto;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.TransactionService;
import ua.gordeichuk.payments.service.enums.SortType;
import ua.gordeichuk.payments.util.*;
import ua.gordeichuk.payments.entity.Transaction;
import ua.gordeichuk.payments.entity.enums.TransactionType;
import ua.gordeichuk.payments.dto.commandparam.HistoryParamDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Администратор on 05.07.2017.
 */
public class HistoryActionCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(HistoryActionCommand.class);
    private static final String ALL = "ALL";
    private static final String DESC = "DESC";
    private TransactionService transactionService;
    private Validator validator = Validator.getInstance();

    public HistoryActionCommand(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

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
        MessageDto messageDto = new MessageDto.Builder()
                .addMessage(Message.TRANSACTIONS_NOT_FOUND)
                .build();
        request.setAttribute(Attribute.MESSAGE_ERROR, messageDto.getMessage());
        LOGGER.info(messageDto.getLogMessage());
    }

    private HistoryParamDto extractHistoryParams(HttpServletRequest request){
        return new HistoryParamDto.Builder()
                .setCardIdString(request.getParameter(Attribute.CARD))
                .setDateFromString(request.getParameter(Attribute.DATE_FROM))
                .setDateToString( request.getParameter(Attribute.DATE_TO))
                .setTransactionTypeString(request.getParameter(Attribute.TRANSACTION_TYPE))
                .setSortTypeString(request.getParameter(Attribute.SORT_TYPE))
                .setLocale(getLocale(request))
                .build();

    }
    private TransactionParamDto validateAndGetParams (HistoryParamDto historyParamDto)
            throws ServiceException {
        Long cardId = validator.validateAndParseCardNumber(historyParamDto.getCardIdString());
        Date dateFrom = validator.validateAndParseDate(historyParamDto.getDateFromString(),
                historyParamDto.getLocale());
        Date dateTo = validator.validateAndParseDate(historyParamDto.getDateToString(),
                historyParamDto.getLocale());
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
    private String getLocale(HttpServletRequest request){
        return (String) request.getSession().getAttribute(Attribute.LOCALE);
    }


    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.SEARCHING_TRANSACTIONS_FAILED);
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.HISTORY;
    }

}
