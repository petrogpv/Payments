package ua.gordeichuk.payments.controller.command;

import ua.gordeichuk.payments.controller.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SearchUnlockActionCommand implements ua.gordeichuk.payments.controller.Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        return null;
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        return null;
    }
}
