package ua.gordeichuk.payments.daojdbc.jdbcimpl.entityimpl.psdtobuilder;

import ua.gordeichuk.payments.dto.entityparam.CardParamDto;
import ua.gordeichuk.payments.dto.entityparam.PreparedStatementDto;
import ua.gordeichuk.payments.dto.entityparam.TransactionParamDto;
import ua.gordeichuk.payments.service.CardService;
import ua.gordeichuk.payments.util.SqlBundle;

import java.util.ResourceBundle;

/**
 * Created by Администратор on 10.07.2017.
 */
public class CardPsDtoBuilder extends PsDtoBuilder {
    private static final String ENTITY_NAME = "card";
    private static final String FIND_BY_USER_CONDITION = "find.ByUser";
    private static final String FIND_BY_ACCOUNT_CONDITION = "find.ByAccount";
    private static final String FIND_BY_LOGIN_CONDITION = "find.ByLogin";
    private static final String FIND_AND_BY_CARD_STATUS = "find.AndByCardStatus";

    public CardPsDtoBuilder() {
        super(ENTITY_NAME);
    }



    public PreparedStatementDto buildPreparedStatementDto(CardParamDto cardParamDto) {
        writeAll();
        writeByUser(cardParamDto);
        writeByAccount(cardParamDto);
        writeByLogin(cardParamDto);
        writeByCardStatus(cardParamDto);
        return preparedStatementDto;
    }


    public void writeByUser(CardParamDto cardParamDto){
        if(cardParamDto.getUserId() !=null){
            preparedStatementDto.writeSql(getSqlString(FIND_BY_USER_CONDITION));
            preparedStatementDto.addParameter(cardParamDto.getUserId());
        }
    }
    public void writeByAccount(CardParamDto cardParamDto){
        if(cardParamDto.getAccountId() !=null){
            preparedStatementDto.writeSql(getSqlString(FIND_BY_ACCOUNT_CONDITION));
            preparedStatementDto.addParameter(cardParamDto.getAccountId());
        }
    }
    public void writeByLogin(CardParamDto cardParamDto){
        if(cardParamDto.getLogin() !=null){
            preparedStatementDto.writeSql(getSqlString(FIND_BY_LOGIN_CONDITION));
            preparedStatementDto.addParameter(cardParamDto.getLogin());
        }
    }
    public void writeByCardStatus(CardParamDto cardParamDto){
        if(cardParamDto.getCardStatus() !=null){
            preparedStatementDto.writeSql(getSqlString(FIND_AND_BY_CARD_STATUS));
            preparedStatementDto.addParameter(cardParamDto.getCardStatus());
        }
    }

}
