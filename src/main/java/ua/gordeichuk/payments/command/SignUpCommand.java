package ua.gordeichuk.payments.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.Command;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.UserService;
import ua.gordeichuk.payments.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 30.06.2017.
 */
public class SignUpCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(SignUpCommand.class);
    private UserService userService;

    public SignUpCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
            return Page.SIGNUP;
    }
}
