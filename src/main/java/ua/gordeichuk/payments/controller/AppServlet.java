package ua.gordeichuk.payments.controller;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.util.Page;
import ua.gordeichuk.payments.util.RequestAttribute;

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
    private static final String EXTENSION = ".jsp";

    private static final CommandGetter commandGetter = CommandGetter.getInstance();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String login = (String) request.getAttribute(RequestAttribute.USERNAME);
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("$$$$$$$$$in process request");
            String path = request.getServletPath();
            Command command = commandGetter.getCommand(path);
            String page = command.execute(request, response);
//            if(page.endsWith(EXTENSION)){
                request.getRequestDispatcher(page).forward(request, response);
//            } else {
//                response.sendRedirect(page);
////                request.getRequestDispatcher(page).forward(request, response);
//            }
        } catch ( ServletException | IOException e) {
            LOGGER.warn("Warning!", e);
            try {
                request.getRequestDispatcher(Page.ERROR_PAGE).forward(request, response);
            } catch (ServletException | IOException e1) {
                LOGGER.warn("Warning!", e1);
                //nothing to do here
            }
        } catch (Exception e) {
            LOGGER.error("Global error!", e);
//            try {
//                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
//            } catch (ServletException | IOException e1) {
//                LOGGER.warn("Warning!", e1);
//                //nothing to do here
//            }
        }
    }
}
