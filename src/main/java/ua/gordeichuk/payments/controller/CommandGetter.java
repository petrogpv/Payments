package ua.gordeichuk.payments.controller;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.command.*;
import ua.gordeichuk.payments.controller.service.CardService;
import ua.gordeichuk.payments.controller.service.TransactionService;
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
        commands.put(Path.MANAGE, new ManageCommand());
        commands.put(Path.MANAGE_ACTION, new ManageActionCommand(CardService.getInstance()));
        commands.put(Path.ADD_USER, new AddUserCommand());
        commands.put(Path.ADD_USER_ACTION, new AddUserActionCommand());
        commands.put(Path.HISTORY, new HistoryCommand(CardService.getInstance()));
        commands.put(Path.HISTORY_ACTION, new HistoryActionCommand(TransactionService.getInstance()));
        commands.put(Path.SEARCH_UNLOCK, new SearchUnlockCommand());
        commands.put(Path.SEARCH_UNLOCK_ACTION, new SearchUnlockActionCommand());
        commands.put(Path.LOCK, new LockCommand(CardService.getInstance()));
        commands.put(Path.DEPOSIT, new DepositCommand(CardService.getInstance()));
        commands.put(Path.DEPOSIT_ACTION, new DepositActionCommand(CardService.getInstance()));
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
