package ua.gordeichuk.payments.controller.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.controller.Validator;
import ua.gordeichuk.payments.controller.exception.ServiceException;
import ua.gordeichuk.payments.controller.service.TransactionService;
import ua.gordeichuk.payments.controller.util.*;
import ua.gordeichuk.payments.model.entity.Transaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
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

    public HistoryActionCommand(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String cardIdString = request.getParameter(Attribute.CARD);
        String dateFromString = request.getParameter(Attribute.DATE_FROM);
        String dateToString = request.getParameter(Attribute.DATE_TO);
        String transactionTypeString = request.getParameter(Attribute.TRANSACTION_TYPE);
        String sortType = request.getParameter(Attribute.SORT_TYPE);


        Long cardId = Validator.validateAndParseCardNumber(cardIdString);
        String locale = (String) request.getSession().getAttribute(Attribute.LOCALE);

        Date dateFrom = Validator.validateAndParseDate(dateFromString, locale);
        Date dateTo = Validator.validateAndParseDate(dateToString, locale);
        Validator.validateDates(dateFrom, dateTo);
        Validator.validateSortType(sortType);

        List<Transaction> transactions;
        if (transactionTypeString.equals(ALL)) {
            transactions = transactionService.findManyByCardAndByDates(cardId, dateFrom, dateTo);
        } else {
            transactions = transactionService.findManyByCardAndByDatesAndByType(cardId,
                    dateFrom, dateTo, transactionTypeString);
        }

        Collections.sort(transactions);
        transactions.stream().forEach(System.out::println);

        if (sortType.equals(DESC)) {
            Collections.sort(transactions, Collections.reverseOrder());
        }
        transactions.stream().forEach(System.out::println);
        if (transactions.isEmpty()) {
            String logMessage = Message.getLogMessage(Message.TRANSACTIONS_NOT_FOUND)
                    + Message.getLogMessage(Message.TRANSACTION_TYPE)
                    + Message.getLogMessage(Message.TRANSACTION_TYPE) + transactionTypeString
                    + Message.getLogMessage(Message.FOR_CARD) + cardId;
            if (dateFrom != null) {
                logMessage += Message.getLogMessage(Message.DATE_FROM)
                        + Parser.parseDateToString(dateFrom, locale);
            }
            if (dateTo != null) {
                logMessage += Message.getLogMessage(Message.DATE_TO)
                        + Parser.parseDateToString(dateTo, locale);
            }


            String message = Message.getMessage(Message.TRANSACTIONS_NOT_FOUND)
                    + Message.getMessage(Message.TRANSACTION_TYPE)
                    + Message.getTransactionTypeMessage(transactionTypeString)
                    + Message.getMessage(Message.FOR_CARD) + cardId;
            if (dateFrom != null) {
                message += Message.getMessage(Message.DATE_FROM)
                        + Parser.parseDateToString(dateFrom, locale);
            }
            if (dateTo != null) {
               message += Message.getMessage(Message.DATE_TO)
                       + Parser.parseDateToString(dateTo, locale);
            }

            request.setAttribute(Attribute.MESSAGE_ERROR, message);
            LOGGER.info(logMessage);
        } else {
            int foundTransactions = transactions.size();
            LOGGER.info(LogMessage.TRANSACTIONS_FOUND + foundTransactions);
            request.setAttribute(Attribute.TRANSACTIONS, transactions);
        }
        return Path.HISTORY;
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.SEARCHING_TRANSACTIONS_FAILED);
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.HISTORY;
    }
}
