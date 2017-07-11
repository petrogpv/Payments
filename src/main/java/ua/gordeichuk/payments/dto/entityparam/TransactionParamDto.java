package ua.gordeichuk.payments.dto.entityparam;

import ua.gordeichuk.payments.entity.enums.TransactionType;
import ua.gordeichuk.payments.service.enums.SortType;

import java.util.Date;

/**
 * Created by Администратор on 08.07.2017.
 */
public class TransactionParamDto {

    private Long cardId;
    private Date dateFrom;
    private Date dateTo;
    private TransactionType transactionType;
    private SortType sortType;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public static class Builder{
        private TransactionParamDto transactionParamDto =
                new TransactionParamDto();

        public Builder setCardId(Long cardId){
            transactionParamDto.setCardId(cardId);
            return this;
        }
        public Builder setDateFrom(Date dateFrom){
            transactionParamDto.setDateFrom(dateFrom);
            return this;
        }
        public Builder setDateTo(Date dateTo){
            transactionParamDto.setDateTo(dateTo);
            return this;
        }
        public Builder setTransactionType(TransactionType transactionType){
            transactionParamDto.setTransactionType(transactionType);
            return this;
        }
        public Builder setSortType(SortType sortType) {
            transactionParamDto.setSortType(sortType);
            return this;
        }

        public TransactionParamDto build(){
            return transactionParamDto;
        }
    }

}
