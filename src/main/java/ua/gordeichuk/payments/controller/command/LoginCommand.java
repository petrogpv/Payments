package ua.gordeichuk.payments.controller.command;

import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.util.Path;
import ua.gordeichuk.payments.util.RequestAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Created by Администратор on 29.06.2017.
 */
public class LoginCommand implements Command {

    private static final String LOGIN_EMAIL_REGEX =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String username =  request.getParameter(RequestAttribute.USERNAME);
        String password = request.getParameter(RequestAttribute.PASSWORD);
        Enumeration<String> stringEnumeration = request.getAttributeNames();
        while(stringEnumeration.hasMoreElements()){
            System.out.println(stringEnumeration.nextElement());
        }
        System.out.println("LOGIN --- " + username);
        System.out.println("PASS --- " + password);
        if(!username.matches(LOGIN_EMAIL_REGEX)){
            request.setAttribute(RequestAttribute.WRONG_USERNAME, username);
            return Path.DEFAULT_PATH;
        }
        return null;
    }
}
