package ua.gordeichuk.payments.entity;

import ua.gordeichuk.payments.entity.enums.CardStatus;

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
}
