package ua.gordeichuk.payments.controller.parameters.dto;

public class HistoryParamDto {

    private String cardIdString;
    private String dateFromString;
    private String dateToString;
    private String transactionTypeString;
    private String sortTypeString;


    public String getCardIdString() {
        return cardIdString;
    }

    public void setCardIdString(String cardIdString) {
        this.cardIdString = cardIdString;
    }

    public String getDateFromString() {
        return dateFromString;
    }

    public void setDateFromString(String dateFromString) {
        this.dateFromString = dateFromString;
    }

    public String getDateToString() {
        return dateToString;
    }

    public void setDateToString(String dateToString) {
        this.dateToString = dateToString;
    }

    public String getTransactionTypeString() {
        return transactionTypeString;
    }

    public void setTransactionTypeString(String transactionTypeString) {
        this.transactionTypeString = transactionTypeString;
    }

    public String getSortTypeString() {
        return sortTypeString;
    }

    public void setSortTypeString(String sortTypeString) {
        this.sortTypeString = sortTypeString;
    }

    public static class Builder {
        private HistoryParamDto historyParamDto = new HistoryParamDto();

        public Builder setCardIdString(String cardIdString) {
            historyParamDto.setCardIdString(cardIdString);
            return this;
        }

        public Builder setDateFromString(String dateFromString) {
            historyParamDto.setDateFromString(dateFromString);
            return this;
        }

        public Builder setDateToString(String dateToString) {
            historyParamDto.setDateToString(dateToString);
            return this;
        }

        public Builder setTransactionTypeString(String transactionTypeString) {
            historyParamDto.setTransactionTypeString(transactionTypeString);
            return this;
        }

        public Builder setSortTypeString(String sortTypeString) {
            historyParamDto.setSortTypeString(sortTypeString);
            return this;
        }

        public HistoryParamDto build() {
            return historyParamDto;
        }
    }

}
