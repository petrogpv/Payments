package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.daoentity.AccountDao;
import ua.gordeichuk.payments.daoentity.CardDao;
import ua.gordeichuk.payments.daojdbc.DaoConnection;
import ua.gordeichuk.payments.daojdbc.DaoFactory;
import ua.gordeichuk.payments.dto.entityparam.CardParamDto;
import ua.gordeichuk.payments.entity.Account;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.enums.CardStatus;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDto;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;

import java.util.List;
import java.util.Optional;

/**
 * Created by Валерий on 26.06.2017.
 */
public class AccountService {
    private static final Logger LOGGER = Logger.getLogger(AccountService.class);
    public static final long ZERO_BALANCE = 0L;
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private AccountService() {
    }

    private static class Holder {
        static final AccountService INSTANCE = new AccountService();
    }


    public static AccountService getInstance() {
        return Holder.INSTANCE;
    }

    public Optional<Account> getAccountByCard(Long cardId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            AccountDao accountDao = daoFactory.createAccountDao(connection);
            Optional<Account> entity = accountDao.findByCard(cardId);
            connection.commit();
            return entity;
        }
    }
    public List<Account> findAccountsByUser(User user){
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            AccountDao accountDao = daoFactory.createAccountDao(connection);
            List<Account> accounts = accountDao.findManyByUser(user.getId());
            fillAccountsWithCards(accounts, connection);
            connection.commit();
            return accounts;
        }
    }
    public Card addAccountAndCard(Long userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            AccountDao accountDao = daoFactory.createAccountDao(connection);
            CardDao cardDao = daoFactory.createCardDao(connection);
            User user = new User();
            user.setId(userId);
            Account account = new Account();
            account.setBalance(ZERO_BALANCE);
            accountDao.create(account);
            Card card = new Card.Builder()
                    .setUser(user)
                    .setAccount(account)
                    .setCardStatus(CardStatus.ACTIVE)
                    .build();
            cardDao.create(card);
            connection.commit();
            return card;
        }
    }


    public void deleteAccount(Long accountId ) throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            Account account = findAccount(accountId, connection);
            AccountDao accountDao = daoFactory.createAccountDao(connection);
            accountDao.delete(account.getId());
            connection.commit();
        }
    }



    protected void fillAccountWithCards(Account account,DaoConnection connection){
        CardDao cardDao = daoFactory.createCardDao(connection);
        CardParamDto cardParamDto = new CardParamDto.Builder()
                .setAccountId(account.getId())
                .build();
        List<Card> cards = cardDao.findManyByDto(cardParamDto);
        account.setCards(cards);
    }
    private void fillAccountsWithCards(List<Account> accounts,DaoConnection connection){
        if(!accounts.isEmpty()){
            for (Account account : accounts ) {
                fillAccountWithCards(account, connection);
            }
        }
    }
    private Account findAccount(Long accountId, DaoConnection connection )
            throws ServiceException {
        AccountDao accountDao = daoFactory.createAccountDao(connection);
        Optional<Account> accountOptional = accountDao.find(accountId);
        if (!accountOptional.isPresent()) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.ACCOUNT_NOT_EXIST)
                    .addMessage(String.valueOf(accountId))
                    .build();
            throw new ServiceException(messageDto.getMessage());
        }
        return accountOptional.get();
    }


}
