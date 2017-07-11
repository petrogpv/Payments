package ua.gordeichuk.payments.dto.commandparam;

/**
 * Created by Администратор on 09.07.2017.
 */
public class TransferParamDto {
    private Long cardIdFrom;
    private Long cardIdTo;
    private Long value;

    public Long getCardIdFrom() {
        return cardIdFrom;
    }

    public void setCardIdFrom(Long cardIdFrom) {
        this.cardIdFrom = cardIdFrom;
    }

    public Long getCardIdTo() {
        return cardIdTo;
    }

    public void setCardIdTo(Long cardIdTo) {
        this.cardIdTo = cardIdTo;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public static class Builder{
      private TransferParamDto transferParamDto;
        public Builder setCarIdFrom(Long cardIdFrom) {
            transferParamDto.setCardIdFrom(cardIdFrom);
            return this;
        }
        public Builder setCardIdTo(Long cardIdTo){
            transferParamDto.setCardIdTo(cardIdTo);
            return this;
        }
        public Builder setValue(Long value){
            transferParamDto.setValue(value);
            return this;
        }
        public  TransferParamDto build(){
            return transferParamDto;
        }
    }
}