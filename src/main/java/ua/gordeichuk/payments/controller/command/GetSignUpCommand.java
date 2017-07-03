package ua.gordeichuk.payments.controller.command;

import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.controller.exception.ServiceException;
import ua.gordeichuk.payments.controller.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Валерий on 02.07.2017.
 */
public class GetSignUpCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return Page.SIGN_UP;
    }
}
