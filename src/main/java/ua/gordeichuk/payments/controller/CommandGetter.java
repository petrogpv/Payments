package ua.gordeichuk.payments.controller;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.command.DefaultCommand;

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
//        commands.put("/login_page", new CommandLoginPage());
//        commands.put("/login", new CommandLogin());
//        commands.put("/logout", new CommandLogout());
//        commands.put("/registration_page", new CommandRegistrationPage());
//        commands.put("/registration", new CommandRegistration());
//        commands.put("/flights", new CommandFlightsPage());
//        commands.put("/admin/new_flight", new CommandNewFlight());
//        commands.put("/admin/edit_flight", new CommandEditFlight());
//        commands.put("/admin/create_flight", new CommandCreateFlight());
//        commands.put("/admin/delete_flight", new CommandDeleteFlight());
//        commands.put("/admin/save_flight", new CommandSaveFlight());
//        commands.put("/flights_interval", new CommandFlightsInterval());
//        commands.put("/dispatcher/edit_crew", new CommandEditCrew());
//        commands.put("/dispatcher/view_crew", new CommandViewCrew());
//        commands.put("/dispatcher/save_crew", new CommandSaveCrew());
//        commands.put("/admin/users", new CommandUsers());
//        commands.put("/admin/change_role", new CommandChangeRole());
//        commands.put("/admin/update_role", new CommandUpdateRole());
//        commands.put("/user_info", new CommandUserInfo());
//        commands.put("/change_lang", new CommandChangeLanguage());
    }

    public static CommandGetter getInstance() {
        return instance;
    }

    public Command getCommand(String path) {
        LOGGER.info("In get command");
        Command command = commands.get(path);
        if(command == null){
            command = new DefaultCommand();
        }
        return command;
    }
}
