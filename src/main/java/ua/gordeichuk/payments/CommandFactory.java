package ua.gordeichuk.payments;

import ua.gordeichuk.payments.command.*;
import ua.gordeichuk.payments.service.AccountService;
import ua.gordeichuk.payments.service.CardService;
import ua.gordeichuk.payments.service.TransactionService;
import ua.gordeichuk.payments.service.UserService;
import ua.gordeichuk.payments.util.Path;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Администратор on 28.06.2017.
 */
public class CommandFactory {
    private static Map<String, Command> commands = new HashMap<>();
    private static CommandFactory instance = new CommandFactory();

    private CommandFactory() {
        commands.put(Path.SIGNIN, new SignInCommand(UserService.getInstance()));
        commands.put(Path.SIGNUP, new SignUpCommand(UserService.getInstance()));
        commands.put(Path.SIGNUP_ACTION, new SignUpActionCommand(UserService.getInstance()));
        commands.put(Path.SIGNOUT, new SignOutCommand());
        commands.put(Path.MANAGE_CARDS, new ManageCardsCommand());
        commands.put(Path.MANAGE_CARDS_ACTION, new ManageCardsActionCommand(CardService.getInstance()));
        commands.put(Path.MANAGE_USERS, new ManageUsersCommand());
        commands.put(Path.MANAGE_USERS_ACTION,
                new ManageUsersActionCommand(UserService.getInstance(), TransactionService.getInstance()));
        commands.put(Path.ADD_USER, new AddUserCommand());
        commands.put(Path.ADD_USER_ACTION, new AddUserActionCommand(UserService.getInstance()));
        commands.put(Path.ADD_ACCOUNT, new AddAccountCommand(AccountService.getInstance()));
        commands.put(Path.ADD_CARD, new AddCardCommand(CardService.getInstance()));
        commands.put(Path.DELETE_ACCOUNT, new DeleteAccountCommand(AccountService.getInstance()));
        commands.put(Path.DELETE_CARD, new DeleteCardCommand(CardService.getInstance()));
        commands.put(Path.DELETE_USER, new DeleteUserCommand(UserService.getInstance()));
        commands.put(Path.HISTORY, new HistoryCommand(CardService.getInstance()));
        commands.put(Path.HISTORY_ACTION, new HistoryActionCommand(TransactionService.getInstance()));
        commands.put(Path.LOCK, new LockCommand(CardService.getInstance()));
        commands.put(Path.DEPOSIT, new DepositCommand(CardService.getInstance()));
        commands.put(Path.DEPOSIT_ACTION, new DepositActionCommand(CardService.getInstance()));
        commands.put(Path.PAYMENT, new PaymentCommand(CardService.getInstance()));
        commands.put(Path.PAYMENT_ACTION, new PaymentActionCommand(CardService.getInstance()));
        commands.put(Path.CARDS_CHANGE, new CardsChangeCommand(CardService.getInstance()));
    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(String path) {
        Command command = commands.get(path);
        if(command == null){
            command = new DefaultCommand();
        }
        return command;
    }
}
