package ua.gordeichuk.payments.daojdbc.jdbcimpl.entityimpl.psdtobuilder;

import ua.gordeichuk.payments.dto.entityparam.PreparedStatementDto;
import ua.gordeichuk.payments.dto.entityparam.TransactionParamDto;
import ua.gordeichuk.payments.util.SqlBundle;

import java.util.ResourceBundle;


public abstract class PsDtoBuilder {
    private static final String DOT = ".";
    private static final String FIND_ALL_QUERY = "find.All";
    protected String entityName;
    protected static ResourceBundle sqlBundle;
    protected PreparedStatementDto preparedStatementDto = new PreparedStatementDto();

    protected PsDtoBuilder(String entityName) {
        sqlBundle = SqlBundle.getSqlBundle();
        this.entityName = entityName;
    }

    public void writeAll() {
        preparedStatementDto.writeSql(getSqlString(FIND_ALL_QUERY));
    }
    protected String getSqlString(String queryName) {
        return sqlBundle.getString(entityName + DOT + queryName);
    }
}
