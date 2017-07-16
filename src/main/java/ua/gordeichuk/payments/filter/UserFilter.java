package ua.gordeichuk.payments.filter;

import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.enums.UserRole;

import javax.servlet.http.HttpSession;

public class UserFilter extends VisitorFilter {

    boolean isUserAuthorized(HttpSession session) {
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        UserRole userRole = user.getUserAuth().getRole();
        return userRole.equals(UserRole.USER);
    }

}
