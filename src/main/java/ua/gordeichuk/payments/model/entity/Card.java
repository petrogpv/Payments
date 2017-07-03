package ua.gordeichuk.payments.model.entity;

import ua.gordeichuk.payments.model.entity.enums.CardStatus;

/**
 * Created by Валерий on 12.06.2017.
 */
public class Card implements Entity {
    private Long id;
    private User user;
    private Account account;
    private CardStatus status;

    private Card() {
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
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", status=" + status +
                ", user=" + user +
                ", account=" + account +
                '}';
    }
}
