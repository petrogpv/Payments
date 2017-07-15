package ua.gordeichuk.payments.tag;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.util.Attribute;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * Created by Администратор on 06.07.2017.
 */
public class ConvertLongToDoubleStringTag extends TagSupport {
    private static final Logger LOGGER = Logger.getLogger(ConvertLongToDoubleStringTag.class);
    private static final String EN_LOCALE = "en_US";
    private static final String UA_LOCALE = "uk_UA";
    private static final char DOT = '.';
    private static final char COMMA = ',';
    private static final String MINUS = "-";
    private static final String ZERO = "0";
    private static final String DOUBLE_ZERO = "00";
    private static final String TAG_ERROR = "ConvertLongToDoubleStringTag error for value: ";
    private Long value;

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public int doStartTag() throws JspException {
        String result = String.valueOf(value);
        char separator = DOT;
        int length = result.length();

        if (result.startsWith(MINUS) && length == 2) {
            result = MINUS + DOUBLE_ZERO + result.substring(1);
        } else if (result.startsWith(MINUS) && length == 3) {
            result = MINUS + ZERO + result.substring(1);

        } else if (length == 1) {
            result = DOUBLE_ZERO + result;
        } else if (length == 2) {
            result = ZERO + result;
        }

        length = result.length();

        String locale = (String) pageContext.getSession().getAttribute(Attribute.LOCALE);
        if (locale == null || locale.equals(EN_LOCALE)) {
            separator = DOT;
        } else if (locale.equals(UA_LOCALE)) {
            separator = COMMA;
        }
        result = result.substring(0, length - 2)
                + separator + result.substring(length - 2, length);

        try {
            pageContext.getOut().write(result);
        } catch (Exception e) {
            LOGGER.error(TAG_ERROR + value);
        }
        return SKIP_BODY;
    }
}
