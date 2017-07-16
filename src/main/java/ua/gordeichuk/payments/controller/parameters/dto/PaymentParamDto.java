package ua.gordeichuk.payments.controller.parameters.dto;

/**
 * Created by Администратор on 09.07.2017.
 */
public class PaymentParamDto {

    private String cardIdFromString;
    private String cardIdToString;
    private String valueString;

    public String getCardIdFromString() {
        return cardIdFromString;
    }

    public void setCardIdFromString(String cardIdFromString) {
        this.cardIdFromString = cardIdFromString;
    }

    public String getCardIdToString() {
        return cardIdToString;
    }

    public void setCardIdToString(String cardIdToString) {
        this.cardIdToString = cardIdToString;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }
    public static class Builder {
        private PaymentParamDto paymentParamDto = new PaymentParamDto();

        public Builder setCardIdFromString(String cardIdFromString) {
            paymentParamDto.setCardIdFromString(cardIdFromString);
            return this;
        }

        public Builder setCardIdToString(String cardIdToString) {
            paymentParamDto.setCardIdToString(cardIdToString);
            return this;
        }

        public Builder setValueString(String valueString) {
            paymentParamDto.setValueString(valueString);
            return this;
        }

        public PaymentParamDto build() {
            return paymentParamDto;
        }
    }
}
