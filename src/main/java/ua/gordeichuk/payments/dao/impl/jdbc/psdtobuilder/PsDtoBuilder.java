package ua.gordeichuk.payments.dao.impl.jdbc.psdtobuilder;

import ua.gordeichuk.payments.dao.parameters.dto.PreparedStatementDto;
import ua.gordeichuk.payments.util.SqlBundle;

import java.util.ResourceBundle;

public abstract class PsDtoBuilder {
    private static final String DOT = ".";
    private static final String FIND_ALL_QUERY = "find.All";
    protected String entityName;
    protected static ResourceBundle sqlBundle;
    protected PreparedStatementDto preparedStatementDto = new PreparedStatementDto();

    protected PsDtoBuilder(String entityName) {
        sqlBundle = SqlBundle.getInstance().getSqlBundle();
        this.entityName = entityName;
    }

    public void writeAll() {
        preparedStatementDto.writeSql(getSqlString(FIND_ALL_QUERY));
    }

    protected String getSqlString(String queryName) {
        return sqlBundle.getString(entityName + DOT + queryName);
    }
}
