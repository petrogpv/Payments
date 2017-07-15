package ua.gordeichuk.payments.service.localization;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Валерий on 13.07.2017.
 */
public class SessionListener implements HttpSessionListener {
    private int totalActiveSessions = 0;
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        totalActiveSessions++;
        System.out.println("totalActiveSessions + :" + totalActiveSessions);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        String sessionId = httpSessionEvent.getSession().getId();
        SessionLocale.getInstance().removeLocale(sessionId);
        totalActiveSessions--;
        System.out.println("totalActiveSessions - :" + totalActiveSessions);
    }
}
