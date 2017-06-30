package ua.gordeichuk.payments.controller;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Администратор on 28.06.2017.
 */
public class CommandGetter {
    private static Map<String, Command> commands = new HashMap<>();
    private static CommandGetter instance = new CommandGetter();
    private static final Logger LOGGER = Logger.getLogger(CommandGetter.class);


    private CommandGetter() {
        commands.put("/auth/login", new LoginCommand());
        commands.put("/auth/register", new RegisterCommand());
        commands.put("/locale", new LocaleCommand());
        commands.put("/logout", new LogoutCommand());


    }

    public static CommandGetter getInstance() {
        return instance;
    }

    public Command getCommand(String path) {
        LOGGER.info("In get command" + path);
        Command command = commands.get(path);
        if(command == null){
            command = new DefaultCommand();
        }
        return command;
    }
}
