package ua.gordeichuk.payments.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class ValidatorRegexTest {
    Validator validator = Validator.getInstance();


    @Test()
    public void testCardNumberRegex() throws Exception {
        String numberTrue = "550500000003";
        String numberFalse1 = "440400000003";
        String numberFalse2 = "550500000";
        String numberFalse3 = "550500000000001";
        String numberFalse4 = "55050000FF";

        boolean testTrue = numberTrue.matches(Validator.CARD_NUMBER_REGEX);
        boolean testFalse1 = numberFalse1.matches(Validator.CARD_NUMBER_REGEX);
        boolean testFalse2 = numberFalse2.matches(Validator.CARD_NUMBER_REGEX);
        boolean testFalse3 = numberFalse3.matches(Validator.CARD_NUMBER_REGEX);
        boolean testFalse4 = numberFalse4.matches(Validator.CARD_NUMBER_REGEX);

        assertTrue(testTrue);
        assertFalse(testFalse1);
        assertFalse(testFalse2);
        assertFalse(testFalse3);
        assertFalse(testFalse4);

    }

    @Test
    public void testAccountNumberRegex() throws Exception {
        String numberTrue = "2605000003";
        String numberFalse1 = "7705000003";
        String numberFalse2 = "260500000333";
        String numberFalse3 = "26050003";
        String numberFalse4 = "2605EE0003";

        boolean testTrue = numberTrue.matches(Validator.ACCOUNT_NUMBER_REGEX);
        boolean testFalse1 = numberFalse1.matches(Validator.ACCOUNT_NUMBER_REGEX);
        boolean testFalse2 = numberFalse2.matches(Validator.ACCOUNT_NUMBER_REGEX);
        boolean testFalse3 = numberFalse3.matches(Validator.ACCOUNT_NUMBER_REGEX);
        boolean testFalse4 = numberFalse4.matches(Validator.ACCOUNT_NUMBER_REGEX);

        assertTrue(testTrue);
        assertFalse(testFalse1);
        assertFalse(testFalse2);
        assertFalse(testFalse3);
        assertFalse(testFalse4);
    }

    @Test
    public void testMoneyValueRegex() throws Exception {
        String numberTrue1 = "20";
        String numberTrue2 = "20,5";
        String numberTrue3 = "20.05";
        String numberFalse1 = "20.055";
        String numberFalse2 = "20.R";
        String numberFalse3 = "-20";

        boolean testTrue1 = numberTrue1.matches(Validator.MONEY_VALUE_REGEX);
        boolean testTrue2 = numberTrue2.matches(Validator.MONEY_VALUE_REGEX);
        boolean testTrue3 = numberTrue3.matches(Validator.MONEY_VALUE_REGEX);
        boolean testFalse1 = numberFalse1.matches(Validator.MONEY_VALUE_REGEX);
        boolean testFalse2 = numberFalse2.matches(Validator.MONEY_VALUE_REGEX);
        boolean testFalse3 = numberFalse3.matches(Validator.MONEY_VALUE_REGEX);

        assertTrue(testTrue1);
        assertTrue(testTrue2);
        assertTrue(testTrue3);
        assertFalse(testFalse1);
        assertFalse(testFalse2);
        assertFalse(testFalse3);
    }

    @Test
    public void testLoginRegex() throws Exception {
        String loginTrue1 = "petrov@gmail.com";
        String loginTrue2 = "petrov_petr@gmail.com";
        String loginTrue3 = "petrov.petr@gmail.com";
        String loginTrue4 = "petrov-petr@gmail.com";
        String loginFalse1 = "ivanov.gmail.com";
        String loginFalse2 = "ivanov@com";

        boolean testTrue1 = loginTrue1.matches(Validator.LOGIN_EMAIL_REGEX);
        boolean testTrue2 = loginTrue2.matches(Validator.LOGIN_EMAIL_REGEX);
        boolean testTrue3 = loginTrue3.matches(Validator.LOGIN_EMAIL_REGEX);
        boolean testTrue4 = loginTrue4.matches(Validator.LOGIN_EMAIL_REGEX);
        boolean testFalse1 = loginFalse1.matches(Validator.LOGIN_EMAIL_REGEX);
        boolean testFalse2 = loginFalse2.matches(Validator.LOGIN_EMAIL_REGEX);

        assertTrue(testTrue1);
        assertTrue(testTrue2);
        assertTrue(testTrue3);
        assertTrue(testTrue4);
        assertFalse(testFalse1);
        assertFalse(testFalse2);

    }

    @Test
    public void validateFirstNameRegex() throws Exception {
        String nameTrue = "Ivan";
        String nameFalse1 = "IVan";
        String nameFalse2 = "ivan";
        String nameFalse3 = "Ivaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaan"; // 51 symbols length

        boolean testTrue = nameTrue.matches(Validator.FIRST_NAME_REGEX);
        boolean testFalse1 = nameFalse1.matches(Validator.FIRST_NAME_REGEX);
        boolean testFalse2 = nameFalse2.matches(Validator.FIRST_NAME_REGEX);
        boolean testFalse3 = nameFalse3.matches(Validator.FIRST_NAME_REGEX);

        assertTrue(testTrue);
        assertFalse(testFalse1);
        assertFalse(testFalse2);
        assertFalse(testFalse3);

    }

    @Test
    public void validateLastNameRegex() throws Exception {
        String nameTrue1 = "Petrova";
        String nameTrue2 = "Petrova-Sidorova";
        String nameFalse1 = "PEtrova";
        String nameFalse2 = "Petrova-sidorova";

        boolean testTrue1 = nameTrue1.matches(Validator.LAST_NAME_REGEX);
        boolean testTrue2 = nameTrue2.matches(Validator.LAST_NAME_REGEX);
        boolean testFalse1 = nameFalse1.matches(Validator.LAST_NAME_REGEX);
        boolean testFalse2 = nameFalse2.matches(Validator.LAST_NAME_REGEX);

        assertTrue(testTrue1);
        assertTrue(testTrue2);
        assertFalse(testFalse1);
        assertFalse(testFalse2);

    }

    @Test
    public void validatePassword() throws Exception {
        String passwordTrue1 = "Petrov2000";
        String passwordTrue2 = "peTro2";
        String passwordFalse1 = "petrov";
        String passwordFalse2 = "Pe2";

        boolean testTrue1 = passwordTrue1.matches(Validator.PASSWORD_REGEX);
        boolean testTrue2 = passwordTrue2.matches(Validator.PASSWORD_REGEX);
        boolean testFalse1 = passwordFalse1.matches(Validator.PASSWORD_REGEX);
        boolean testFalse2 = passwordFalse2.matches(Validator.PASSWORD_REGEX);

        assertTrue(testTrue1);
        assertTrue(testTrue2);
        assertFalse(testFalse1);
        assertFalse(testFalse2);

    }


}