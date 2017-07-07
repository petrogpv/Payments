package ua.gordeichuk.payments.controller.command;

import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.controller.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 07.07.2017.
 */
public class AddUserActionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return null;
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        return null;
    }
}
