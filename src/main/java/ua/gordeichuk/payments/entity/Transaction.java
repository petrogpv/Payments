package ua.gordeichuk.payments.entity;

import ua.gordeichuk.payments.entity.enums.TransactionType;

import java.util.Date;

/**
 * Created by Валерий on 12.06.2017.
 */
public class Transaction implements Entity, Comparable<Transaction>, Cloneable {
    private Long id;
    private Card card;
    private Transaction relativeTransaction;
    private Long balanceAfter;
    private Long value;
    private Date date;
    private TransactionType type;

    public Transaction(){}

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

    public Transaction getRelativeTransaction() {
        return relativeTransaction;
    }

    public void setRelativeTransaction(Transaction relativeTransaction) {
        this.relativeTransaction = relativeTransaction;
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

    @Override
    public int compareTo(Transaction transaction) {
        return this.date.compareTo(transaction.getDate());
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

        public Builder setRelativeTransaction(Transaction t) {
            transaction.relativeTransaction = t;
            return this;
        }

        public Builder setBalanceAfter(Long balanceAfter) {
            transaction.balanceAfter = balanceAfter;
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

//    @Override
//    public int compare(Transaction o1, Transaction o2) {
//        return o1.getDate().compareTo(o2.getDate());
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (!id.equals(that.id)) return false;
        if (card != null ? !card.equals(that.card) : that.card != null) return false;
        if (relativeTransaction != null ? !relativeTransaction.equals(that.relativeTransaction) : that.relativeTransaction != null) return false;
        if (balanceAfter != null ? !balanceAfter.equals(that.balanceAfter) : that.balanceAfter != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return type == that.type;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (card != null ? card.hashCode() : 0);
        result = 31 * result + (relativeTransaction != null ? relativeTransaction.hashCode() : 0);
        result = 31 * result + (balanceAfter != null ? balanceAfter.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public Transaction clone() throws CloneNotSupportedException {
        return (Transaction) super.clone();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
//                ", relativeTransaction=" + relativeTransaction +
                ", type=" + type +
//                ", card=" + card +
//                ", balanceAfter=" + balanceAfter +
//                ", value=" + value +
//                ", date=" + date +
                '}';
    }
}
