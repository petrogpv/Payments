package ua.gordeichuk.payments.entity;

import ua.gordeichuk.payments.entity.enums.AccountStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Валерий on 12.06.2017.
 */
public class Account implements Entity{
    private  Long id;
    private List<Card> cards = new ArrayList<>();
    private Long balance;
    private AccountStatus status;

    private Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }


    public static class Builder {
        private Account account = new Account();

        public Builder setCardsList (List<Card> cards){
            account.cards = cards;
            return  this;
        }

        public  Builder setBalance (Long balance){
            account.balance = balance;
            return this;
        }

        public Builder setAccountStatus (AccountStatus status){
            account.status = status;
            return this;
        }

        public Builder setId (Long id){
            account.id = id;
            return this;
        }

        public Account build(){
            return account;
        }
    }


}
