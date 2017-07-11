package ua.gordeichuk.payments.filter;

import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.enums.UserRole;

import javax.servlet.http.HttpSession;

/**
 * Created by Администратор on 28.06.2017.
 */
public class AdminFilter extends VisitorFilter {

    boolean isUserAuthorized(HttpSession session) {
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        UserRole userRole = user.getUserAuth().getRole();
        return userRole.equals(UserRole.ADMIN);
    }


}
