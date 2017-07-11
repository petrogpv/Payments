package ua.gordeichuk.payments.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Валерий on 12.06.2017.
 */
public class Account implements Entity, Cloneable{
    private  Long id;
    private List<Card> cards = new ArrayList<>();
    private Long balance;

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Card> getCards() {
        List<Card> cardsToGet = new ArrayList<>();
        for ( Card card: cards ) {
            try {
                cardsToGet.add(card.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return cardsToGet;
    }

    public void setCards(List<Card> cards) {
        this.cards = new ArrayList<>();
        for (Card card : cards ) {
            try {
                this.cards.add(card.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public static class Builder {
        private Account account = new Account();

        public Builder setCardsList (List<Card> cards){
            account.cards = new ArrayList<>();
            for (Card card : cards ) {
                try {
                    account.cards.add(card.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            account.cards = cards;
            return this;
        }

        public  Builder setBalance (Long balance){
            account.balance = balance;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (!id.equals(account.id)) return false;
        if (cards != null ? !cards.equals(account.cards) : account.cards != null) return false;
        return balance != null ? balance.equals(account.balance) : account.balance == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (cards != null ? cards.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }

    @Override
    public Account clone() throws CloneNotSupportedException {
        return (Account)super.clone();
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", cards=" + cards +
                '}';
    }
}
