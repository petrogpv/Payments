package ua.gordeichuk.payments.controller;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.command.*;
import ua.gordeichuk.payments.controller.service.CardService;
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
        commands.put(Path.SIGNUP, new SignUpCommand(UserService.getInstance()));
        commands.put(Path.SIGNUP_ACTION, new SignUpActionCommand(UserService.getInstance()));
        commands.put(Path.SIGNOUT, new SignOutCommand());
        commands.put(Path.LOCALE, new LocaleCommand());
        commands.put(Path.MANAGEMENT, new ManagementCommand());
        commands.put(Path.HISTORY, new HistoryCommand());
        commands.put(Path.SEARCH, new SearchCommand());
        commands.put(Path.LOCK, new LockCommand(CardService.getInstance()));
        commands.put(Path.DEPOSIT, new DepositCommand(CardService.getInstance()));
        commands.put(Path.PAYMENT, new PaymentCommand(CardService.getInstance()));
        commands.put(Path.PAYMENT_ACTION, new PaymentActionCommand(CardService.getInstance()));

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
