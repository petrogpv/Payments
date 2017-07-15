package ua.gordeichuk.payments.command;

import ua.gordeichuk.payments.Command;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return Page.ADD_USER;
    }
}
