package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.AccountDao;
import ua.gordeichuk.payments.dao.CardDao;
import ua.gordeichuk.payments.dao.TransactionDao;
import ua.gordeichuk.payments.dao.DaoConnection;
import ua.gordeichuk.payments.dao.DaoFactory;
import ua.gordeichuk.payments.controller.parameters.dto.TransferParamDto;
import ua.gordeichuk.payments.dao.parameters.dto.CardParamDto;
import ua.gordeichuk.payments.entity.Account;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.Transaction;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.enums.CardStatus;
import ua.gordeichuk.payments.entity.enums.TransactionType;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDto;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;
import ua.gordeichuk.payments.util.LogMessage;

import java.util.*;

public class CardService {
    private static final Logger LOGGER = Logger.getLogger(CardService.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private CardService() {
    }

    private static class Holder {
        static final CardService INSTANCE = new CardService();
    }

    public static CardService getInstance() {
        return Holder.INSTANCE;
    }

    public void addCard(Card card) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            CardDao cardDao = daoFactory.createCardDao(connection);
            cardDao.create(card);
            connection.commit();
        }
    }

    public void deleteCard(Long cardId) throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            Card card = findCard(cardId, connection);

            AccountDao accountDao = daoFactory.createAccountDao(connection);
            Account account = accountDao.findByCard(cardId).get();
            AccountService accountService = AccountService.getInstance();
            accountService.fillAccountWithCards(account, connection);
            if (account.getCards().size() == 1) {
                accountDao.delete(account.getId());
            } else {
                CardDao cardDao = daoFactory.createCardDao(connection);
                cardDao.delete(card.getId());
            }
            connection.commit();
        }
    }

    public Card findCardById(Long cardId) throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            Card card = findCard(cardId, connection);
            connection.commit();
            return card;
        }
    }

    public List<Card> findCardsByUser(User user) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            CardDao cardDao = daoFactory.createCardDao(connection);
            CardParamDto cardParamDto = new CardParamDto();
            cardParamDto.setUserId(user.getId());
            List<Card> list = cardDao.findManyByDto(cardParamDto);
            connection.commit();
            return list;
        }
    }

    public List<Card> findCardsByLogin(String login) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            CardDao cardDao = daoFactory.createCardDao(connection);
            CardParamDto cardParamDto = new CardParamDto();
            cardParamDto.setLogin(login);
            List<Card> list = cardDao.findManyByDto(cardParamDto);
            connection.commit();
            return list;
        }
    }

    public boolean lockCard(Long cardId) throws ServiceException {
        return changeCardStatus(cardId, CardStatus.LOCKED);
    }

    public boolean unlockCard(Long cardId) throws ServiceException {
        return changeCardStatus(cardId, CardStatus.ACTIVE);
    }

    public boolean deactivateCard(Long cardId) throws ServiceException {
        return changeCardStatus(cardId, CardStatus.DEACTIVATED);
    }

    public boolean changeCardStatus(Long cardId, CardStatus cardStatus)
            throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            Card card = findCard(cardId, connection);
            card.setStatus(cardStatus);
            CardDao cardDao = daoFactory.createCardDao(connection);
            cardDao.update(card);
            connection.commit();
            LOGGER.info(LogMessage.CARD_STATUS_CHANGED + card.getId()
                    + LogMessage.TO + cardStatus.name());
            return true;
        }
    }

    public boolean transfer(TransferParamDto transferParamDto)
            throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            Long cardIdFrom = transferParamDto.getCardIdFrom();
            Long cardIdTo = transferParamDto.getCardIdTo();
            Long value = transferParamDto.getValue();
            Card cardFrom;
            Card cardTo;
            if (cardIdFrom < cardIdTo) {
                cardFrom = findCard(cardIdFrom, connection);
                cardTo = findCard(cardIdTo, connection);
            } else {
                cardTo = findCard(cardIdTo, connection);
                cardFrom = findCard(cardIdFrom, connection);
            }

            checkCardForActivity(cardFrom);
            checkCardForAvailability(cardTo);

            updateCardDeduct(cardFrom, value, connection);
            updateCardAdd(cardTo, value, connection);

            saveTransactions(cardFrom, cardTo, value, connection);
            connection.commit();
            return true;
        }
    }

    private void saveTransactions(Card cardFrom, Card cardTo, Long sum, DaoConnection connection) {
        Date date = Calendar.getInstance().getTime();

        Transaction transactionFrom = new Transaction.Builder()
                .setCard(cardFrom)
                .setBalanceAfter(cardFrom.getAccount().getBalance())
                .setValue(sum)
                .setType(TransactionType.OUTCOME)
                .setDate(date)
                .build();

        Transaction transactionTo = new Transaction.Builder()
                .setCard(cardTo)
                .setBalanceAfter(cardTo.getAccount().getBalance())
                .setValue(sum)
                .setType(TransactionType.INCOME)
                .setDate(date)
                .build();

        TransactionDao transactionDao = daoFactory.createTransactionDao(connection);

        transactionDao.create(transactionFrom);
        transactionDao.create(transactionTo);

        transactionFrom.setRelativeTransaction(transactionTo);
        transactionTo.setRelativeTransaction(transactionFrom);

        transactionDao.update(transactionFrom);
        transactionDao.update(transactionTo);
    }

    private void updateCardDeduct(Card card, Long value, DaoConnection connection)
            throws ServiceException {
        Account accountFrom = card.getAccount();
        Long balance = getBalanceAndCheckSufficiency(value, accountFrom);
        accountFrom.setBalance(balance - value);
        AccountDao accountDao = daoFactory.createAccountDao(connection);
        accountDao.update(accountFrom);

    }

    private void updateCardAdd(Card card, Long value, DaoConnection connection) {
        Account accountTo = card.getAccount();
        Long balanceBeforeTo = accountTo.getBalance();
        accountTo.setBalance(balanceBeforeTo + value);
        AccountDao accountDao = daoFactory.createAccountDao(connection);
        accountDao.update(accountTo);
    }

    private Long getBalanceAndCheckSufficiency(Long sum, Account accountFrom)
            throws ServiceException {
        Long balanceBeforeFrom = accountFrom.getBalance();
        if (balanceBeforeFrom < sum) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.NOT_ENOUGH_MONEY)
                    .addMessage(String.valueOf(accountFrom.getId()))
                    .build();
            LOGGER.error(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        }
        return balanceBeforeFrom;
    }

    private Card findCard(Long cardId, DaoConnection connection) throws ServiceException {
        CardDao cardDao = daoFactory.createCardDao(connection);
        Optional<Card> cardOptional = cardDao.find(cardId);
        if (!cardOptional.isPresent()) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.CARD_NOT_EXIST)
                    .addMessage(String.valueOf(cardId))
                    .build();
            throw new ServiceException(messageDto.getMessage());
        }
        return cardOptional.get();
    }

    private void checkCardForActivity(Card cardFrom) throws ServiceException {
        if (!cardFrom.getStatus().equals(CardStatus.ACTIVE)) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.CARD_NOT_ACTIVE)
                    .addMessage(String.valueOf(cardFrom.getId()))
                    .build();
            LOGGER.error(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        }
    }

    private void checkCardForAvailability(Card cardTo) throws ServiceException {
        if (cardTo.getStatus().equals(CardStatus.DEACTIVATED)
                || cardTo.getStatus().equals(CardStatus.EXPIRED)) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.CARD_IS_NOT_AVAILABLE)
                    .addMessage(String.valueOf(cardTo.getId()))
                    .build();
            LOGGER.error(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        }
    }

    public boolean deposit(Long cardId, Long sum) throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            Card card = findCard(cardId, connection);
            Account account = card.getAccount();
            account.setBalance(account.getBalance() + sum);
            AccountDao accountDao = daoFactory.createAccountDao(connection);
            accountDao.update(account);
            saveTransactionDeposit(card, sum, connection);
            connection.commit();
            LOGGER.info(LogMessage.DEPOSITING_SUCCESSFUL);
            return true;
        }
    }

    public void saveTransactionDeposit(Card card, Long sum, DaoConnection connection) {
        Date date = Calendar.getInstance().getTime();

        Transaction transaction = new Transaction.Builder()
                .setCard(card)
                .setBalanceAfter(card.getAccount().getBalance())
                .setValue(sum)
                .setType(TransactionType.DEPOSIT)
                .setDate(date)
                .build();

        TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
        transactionDao.create(transaction);
    }

}
