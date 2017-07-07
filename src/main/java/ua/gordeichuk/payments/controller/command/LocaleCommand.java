package ua.gordeichuk.payments.controller.command;

import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.controller.util.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 30.06.2017.
 */
public class LocaleCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        return Path.DEFAULT;
    }
}
