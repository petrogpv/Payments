package ua.gordeichuk.payments.dto.entityparam;

import ua.gordeichuk.payments.entity.enums.CardStatus;

/**
 * Created by Администратор on 10.07.2017.
 */
public class CardParamDto {
    private Long userId;
    private Long accountId;
    private String login;
    private CardStatus cardStatus;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public class Builder {
        private CardParamDto cardParamDto = new CardParamDto();

        public Builder setUserId(Long userId) {
            cardParamDto.setUserId(userId);
            return this;
        }

        public Builder setAccountId(Long accountId) {
           cardParamDto.setAccountId(accountId);
            return this;
        }
        public Builder setLogin(String login) {
           cardParamDto.setLogin(login);
            return this;
        }

        public Builder setCardStatus(CardStatus cardStatus) {
            cardParamDto.setCardStatus(cardStatus);
            return this;
        }

        public CardParamDto build() {
            return cardParamDto;
        }
    }
}
