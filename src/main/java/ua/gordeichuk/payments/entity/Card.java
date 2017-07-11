package ua.gordeichuk.payments.entity;

import ua.gordeichuk.payments.entity.enums.CardStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Валерий on 12.06.2017.
 */
public class Card implements Entity, Cloneable {
    private Long id;
    private User user;
    private Account account;
    private CardStatus status;
    private List<Transaction> transactions = new ArrayList<>();

    public Card() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public List<Transaction> getTransactions() {
        List<Transaction> transactionsToGet = new ArrayList<>();
        for (Transaction transaction : transactions ) {
            try {
                transactionsToGet.add(transaction.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
         return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = new ArrayList<>();
        for (Transaction transaction: transactions) {
            try {
                this.transactions.add(transaction.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public  static class Builder{
        private Card card = new Card();

        public Builder setUser (User user){
            card.user = user;
            return this;
        }
        public Builder setAccount (Account account){
            card.account = account;
            return this;
        }
        public Builder setCardStatus (CardStatus cardStatus){
            card.status = cardStatus;
            return this;
        }

        public Builder setId (Long id){
            card.id = id;
            return this;
        }
        public Builder setTransactions (List<Transaction> transactions){
            card.transactions = new ArrayList<>();
            for (Transaction transaction: transactions) {
                try {
                    card.transactions.add(transaction.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            return this;
        }

        public Card build(){
            return card;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!id.equals(card.id)) return false;
        if (user != null ? !user.equals(card.user) : card.user != null) return false;
        if (account != null ? !account.equals(card.account) : card.account != null) return false;
        return status == card.status;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public Card clone() throws CloneNotSupportedException {
        return (Card) super.clone();
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", status=" + status +
                ", user=" + user +
                ", account=" + account +
                '}';
    }
}
