package ua.gordeichuk.payments;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;
import ua.gordeichuk.payments.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AppServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AppServlet.class);
    private static final CommandFactory COMMAND_FACTORY = CommandFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)throws ServletException, IOException {

            previousPathHandling(request);
            String path = request.getServletPath();
            Command command = COMMAND_FACTORY.getCommand(path);
            String pathToGo;
            try {
                pathToGo = command.execute(request, response);
            } catch (ServiceException e) {
                pathToGo = command.doOnError(request, e);
            }
            catch (RuntimeException e) {

                LOGGER.error(LogMessage.ERROR_NOT_IDENTIFIED, e);
                System.out.println("e.getMessage() ---- " + e.getMessage());
                request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
                pathToGo = Page.ERROR_PAGE;
            }
            request.getRequestDispatcher(pathToGo).forward(request, response);

    }
    private void previousPathHandling(HttpServletRequest request) {
        request.setAttribute(Attribute.PREVIOUS_PATH, request.getServletPath());
    }
}
