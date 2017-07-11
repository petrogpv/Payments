package ua.gordeichuk.payments.daojdbc.jdbcimpl.entityimpl.psdtobuilder;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dto.entityparam.TransactionParamDto;
import ua.gordeichuk.payments.dto.entityparam.PreparedStatementDto;
import ua.gordeichuk.payments.service.enums.SortType;
import ua.gordeichuk.payments.util.SqlBundle;

import java.util.ResourceBundle;

/**
 * Created by Администратор on 08.07.2017.
 */
public class TransactionPsDtoBuilder extends PsDtoBuilder {
    private static final String ENTITY_NAME = "transaction";
    private static final String FIND_BY_CARD_CONDITION = "find.ByCard";
    private static final String FIND_AND_BY_DATE_AFTER = "find.AndByDateAfter";
    private static final String FIND_AND_BY_DATE_BEFORE = "find.AndByDateBefore";
    private static final String FIND_AND_BY_ORDER_DATE_ASC = "find.AndByOrderDateAsc";
    private static final String FIND_AND_BY_ORDER_DATE_DESC = "find.AndByOrderDateDesc";
    private static final String FIND_AND_BY_TYPE = "find.AndByType";

    public TransactionPsDtoBuilder() {
        super(ENTITY_NAME);
    }

    public PreparedStatementDto buildPreparedStatementDto(TransactionParamDto transactionParamDto){
        writeAll();
        writeByCard(transactionParamDto);
        writeByType(transactionParamDto);
        writeByDateFrom(transactionParamDto);
        writeByDateTo(transactionParamDto);
        writeByOrderDate(transactionParamDto);
        return preparedStatementDto;
    }

    private void writeByCard(TransactionParamDto transactionParamDto) {
        if (transactionParamDto.getCardId() != null)
            preparedStatementDto.writeSql(getSqlString(FIND_BY_CARD_CONDITION));
        preparedStatementDto.addParameter(transactionParamDto.getCardId());
    }
    private void writeByDateFrom(TransactionParamDto transactionParamDto) {
        if(transactionParamDto.getDateFrom() != null){
            preparedStatementDto.writeSql(getSqlString(FIND_AND_BY_DATE_AFTER));
            preparedStatementDto.addParameter(transactionParamDto.getDateFrom());
        }
    }
    private void writeByDateTo(TransactionParamDto transactionParamDto) {
        if(transactionParamDto.getDateTo() != null){
            preparedStatementDto.writeSql(getSqlString(FIND_AND_BY_DATE_BEFORE));
            preparedStatementDto.addParameter(transactionParamDto.getDateTo());
        }
    }
    private void writeByOrderDate(TransactionParamDto transactionParamDto) {

        if(transactionParamDto.getSortType() != null){
            SortType sortType = transactionParamDto.getSortType();
            if(sortType.equals(SortType.ASC)){
                preparedStatementDto.writeSql(getSqlString(FIND_AND_BY_ORDER_DATE_ASC));
            }else{
                preparedStatementDto.writeSql(getSqlString(FIND_AND_BY_ORDER_DATE_DESC));
            }

        }
    }
    private void writeByType(TransactionParamDto transactionParamDto) {
        if(transactionParamDto.getTransactionType() != null){
            preparedStatementDto.writeSql(getSqlString(FIND_AND_BY_TYPE));
            preparedStatementDto.addParameter(transactionParamDto.getTransactionType().name());
        }
    }

}
