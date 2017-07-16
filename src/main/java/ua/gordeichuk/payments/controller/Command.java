package ua.gordeichuk.payments.controller;

import ua.gordeichuk.payments.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents action that will perform some logic
 */
public interface Command {

    /**
     * Executes some action, returns address to which request will be forwarded
     *
     * @param request           HTTP request from servlet
     * @param response          HTTP response from servlet
     * @return                  address to which request will be forwarded
     * @throws ServiceException  occurs as result of handling wrong data inputted by user
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException;

    /**
     * handles an exception if it occurs in method execute
     * @param request           HttpServletRequest instance from servlet
     * @param e                 exception occurred in method execute
     * @return                  address to JSP page with message about error
     * @throws RuntimeException if method not overridden
     */
    default String doOnError(HttpServletRequest request, Exception e) {
            throw new RuntimeException(e);
    }
}
