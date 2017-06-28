package ua.gordeichuk.payments.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 28.06.2017.
 */
public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response);
}
