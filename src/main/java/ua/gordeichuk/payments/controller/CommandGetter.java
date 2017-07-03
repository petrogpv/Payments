package ua.gordeichuk.payments.controller;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.command.*;
import ua.gordeichuk.payments.controller.service.UserService;
import ua.gordeichuk.payments.controller.util.Path;

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
        commands.put(Path.SIGNIN, new SignInCommand(UserService.getInstance()));
        commands.put(Path.GET_SIGN_UP, new GetSignUpCommand());
        commands.put(Path.SIGNUP, new SignUpCommand(UserService.getInstance()));
        commands.put(Path.LOCALE, new LocaleCommand());
        commands.put(Path.SIGNOUT, new SignOutCommand());



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
