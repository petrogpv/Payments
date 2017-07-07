package ua.gordeichuk.payments.controller.command;

import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.controller.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
