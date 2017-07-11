package ua.gordeichuk.payments.command;

import ua.gordeichuk.payments.Command;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 11.07.2017.
 */
public class ManageUsersCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return Page.MANAGE_USERS;
    }
}
