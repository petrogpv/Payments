package ua.gordeichuk.payments.dto.entityparam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 08.07.2017.
 */
public class PreparedStatementDto {
    private StringBuilder sbSql = new StringBuilder();
    private List<Object> parameters = new ArrayList<>();

    public void setSql(String sql) {
        sbSql.setLength(0);
        sbSql.append(sql);
    }

    public String getSql() {
        return sbSql.toString();
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void writeSql(String sql) {
        sbSql.append(sql);
    }

    public void addParameter(Object parameter) {
        parameters.add(parameter);
    }
}
