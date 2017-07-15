package ua.gordeichuk.payments.command;

import ua.gordeichuk.payments.Command;
import ua.gordeichuk.payments.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
            return Page.INDEX;
    }
}
