package ua.gordeichuk.payments.controller;

/**
 * Created by Валерий on 30.06.2017.
 */
public class Validator {
    private static final String LOGIN_EMAIL_REGEX =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,20}$";

    public static boolean isEmailCorrect(String email) {
        return email.matches(LOGIN_EMAIL_REGEX);
    }


    public static boolean isPasswordCorrect(String password) {
        return password.matches(PASSWORD_REGEX);
    }
}
