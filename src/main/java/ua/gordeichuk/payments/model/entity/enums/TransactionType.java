package ua.gordeichuk.payments.model.entity.enums;

/**
 * Created by Валерий on 12.06.2017.
 */
public enum TransactionType {

    INCOME, OUTCOME, DEPOSIT;

//    public static boolean isPartOfTransferBetweenAccounts (TransactionType transactionType){
//        return (!transactionType.equals(DEPOSIT))
//                ? true
//                : false;
//
//    }
//
//    public static boolean isPartOfTransferBetweenAccounts (String string){
//        TransactionType transactionType = TransactionType.valueOf(string);
//        return isPartOfTransferBetweenAccounts(transactionType);
//    }

}
