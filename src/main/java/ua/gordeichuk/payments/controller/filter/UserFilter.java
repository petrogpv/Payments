package ua.gordeichuk.payments.controller.filter;

import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.enums.UserRole;

import javax.servlet.http.HttpSession;

/**
 * Created by Администратор on 28.06.2017.
 */
public class UserFilter extends VisitorFilter{

    boolean isUserEligible(HttpSession session) {
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        UserRole userRole = user.getUserAuth().getRole();
        return userRole.equals(UserRole.USER);
    }

}
