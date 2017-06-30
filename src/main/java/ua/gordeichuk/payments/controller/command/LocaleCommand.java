package ua.gordeichuk.payments.controller.command;

import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.util.ExceptionMessage;
import ua.gordeichuk.payments.util.Path;
import ua.gordeichuk.payments.util.RequestAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 30.06.2017.
 */
public class LocaleCommand implements Command {
    private static final String EN = "en";
    private static final String UA = "ua";
    private static final String EN_LOCALE = "en_US";
    private static final String UA_LOCALE = "uk_UA";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String locale = request.getParameter(RequestAttribute.LOCALE);
        if (locale.equals(EN)) {
            request.getSession().setAttribute(RequestAttribute.LOCALE, EN_LOCALE);
            ExceptionMessage.setLocale(ExceptionMessage.ENGLISH_LOCALE);
        } else if(locale.equals(UA)){
            request.getSession().setAttribute(RequestAttribute.LOCALE, UA_LOCALE);
            ExceptionMessage.setLocale(ExceptionMessage.LOCALE_LOCALE);
        }
        return Path.DEFAULT_PATH;
    }
}
