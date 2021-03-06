package ua.gordeichuk.payments.controller.command.admin;

import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageUsersCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return Page.MANAGE_USERS;
    }
}
