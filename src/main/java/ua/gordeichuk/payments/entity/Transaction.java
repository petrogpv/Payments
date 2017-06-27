package ua.gordeichuk.payments.entity;

import ua.gordeichuk.payments.entity.enums.TransactionType;

import java.util.Date;

/**
 * Created by Валерий on 12.06.2017.
 */
public class Transaction implements Entity {
    private Long id;
    private Card card;
    private Transaction transaction;
    private Long balanceBefore;
    private Long balanceAfter;
    private Long value;
    private Date date;
    private TransactionType type;

    private Transaction(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Long getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(Long balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public Long getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(Long balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public static class Builder{
        private Transaction transaction = new Transaction();

        public Builder setCard(Card card) {
            transaction.card = card;
            return this;
        }
        public Builder setType(TransactionType type) {
            transaction.type = type;
            return this;
        }

        public Builder setTransactionId(Transaction transactionId) {
            transaction.transaction = transactionId;
            return this;
        }
        public Builder setBalanceBefore(Long balanceBefore) {
            transaction.balanceBefore = balanceBefore;
            return this;
        }
        public Builder setBalanceAfter(Long balanceAfter) {
            transaction.balanceBefore = balanceAfter;
            return this;
        }
        public Builder setValue(Long value) {
            transaction.value = value;
            return this;
        }
        public Builder setDate(Date date) {
            transaction.date = date;
            return this;
        }
        public Builder setId(Long id) {
            transaction.id = id;
            return this;
        }

        public Transaction build() {
            return transaction;
        }
    }
}
