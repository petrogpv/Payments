package ua.gordeichuk.payments.command;

import ua.gordeichuk.payments.Command;
import ua.gordeichuk.payments.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 28.06.2017.
 */
public class DefaultCommand implements Command {
    public static final String USER_ATTRIBUTE = "user";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
            return Page.INDEX;
    }
}
