package ua.gordeichuk.payments.controller;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.exception.ServiceException;
import ua.gordeichuk.payments.controller.util.Attribute;
import ua.gordeichuk.payments.controller.util.Message;
import ua.gordeichuk.payments.controller.util.LogMessage;
import ua.gordeichuk.payments.controller.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Администратор on 28.06.2017.
 */
//@WebServlet("/")
public class AppServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AppServlet.class);
    private static final String EN = "en";
    private static final String UA = "ua";
    private static final String EN_LOCALE = "en_US";
    private static final String UA_LOCALE = "uk_UA";
    private static final CommandGetter commandGetter = CommandGetter.getInstance();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String login = (String) request.getAttribute(Attribute.USERNAME);
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {

            localeHandling(request);
            String path = request.getServletPath();
            Command command = commandGetter.getCommand(path);
            String page;
            try {
                page = command.execute(request, response);
            } catch (ServiceException e) {
                page = command.doOnError(request, e);
            }
            catch (RuntimeException e) {

                LOGGER.error(LogMessage.ERROR_NOT_IDENTIFIED, e);

                request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
                request.setAttribute(Attribute.MESSAGE_ERROR_DETAILS, e.getStackTrace());
                page = Page.ERROR_PAGE;
            }
            getServletContext().getRequestDispatcher(page).forward(request, response);

    }
    private void localeHandling(HttpServletRequest request){
        request.setAttribute(Attribute.PREVIOUS_PATH, request.getServletPath());
        String locale = request.getParameter(Attribute.LOCALE);
        setLocale(locale, request);


    }
    private void setLocale(String locale, HttpServletRequest request){
        if(locale!=null) {
            if (locale.equals(EN)) {
                request.getSession().setAttribute(Attribute.LOCALE, EN_LOCALE);
                Message.setLocale(Message.ENGLISH_LOCALE);
                LOGGER.info(LogMessage.SET_LOCALE + locale);
            } else if (locale.equals(UA)) {
                request.getSession().setAttribute(Attribute.LOCALE, UA_LOCALE);
                Message.setLocale(Message.LOCALE_LOCALE);
                LOGGER.info(LogMessage.SET_LOCALE + locale);
            }
        }

    }
}
