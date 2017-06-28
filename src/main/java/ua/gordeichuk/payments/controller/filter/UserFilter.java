package ua.gordeichuk.payments.controller.filter;

import ua.gordeichuk.payments.controller.Pages;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.enums.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Администратор on 28.06.2017.
 */
public class UserFilter extends VisitorFilter{

    boolean checkUser(HttpSession session) {
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        UserRole userRole = user.getUserAuth().getRole();
        return userRole.equals(UserRole.USER) ? true : false;
    }

}
