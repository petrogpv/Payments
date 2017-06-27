package ua.gordeichuk.payments;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Валерий on 21.06.2017.
 */
public class Application {
    private static final Logger LOGGER = Logger.getLogger(Application.class);
    private static SecureRandom random = new SecureRandom();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {

            System.out.println(nextRandomString());
        }

        LOGGER.info("Testing logger");
        LOGGER.info("Testing logger");
        LOGGER.info("Testing logger");

       List<String> list = new ArrayList<>();
        Optional.ofNullable(list.isEmpty() ? null : list.get(0));

        String md5Hex1 = DigestUtils.md5Hex("Ivanov2000");
        String md5Hex2 = DigestUtils.md5Hex("Petrov2000");
        String md5Hex3 = DigestUtils.md5Hex("Sidorov2000");

        String sole1 = nextRandomString();
        String sole2 = nextRandomString();
        String sole3 = nextRandomString();

        String resmd5Hex1 = DigestUtils.md5Hex(md5Hex1 + sole1);
        String resmd5Hex2 = DigestUtils.md5Hex(md5Hex2 + sole2);
        String resmd5Hex3 = DigestUtils.md5Hex(md5Hex3 + sole3);

        System.out.println(resmd5Hex1 + "  " + sole1);
        System.out.println(resmd5Hex2 + "  " + sole2);
        System.out.println(resmd5Hex3 + "  " + sole3);


    }

    public static String nextRandomString() {
        return new BigInteger(80, random).toString(16);
    }


}