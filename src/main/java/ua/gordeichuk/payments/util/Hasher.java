package ua.gordeichuk.payments.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Hasher {
    private static final SecureRandom random = new SecureRandom();

    public static String getHash(String password, String sole) {
        return DigestUtils.md5Hex(DigestUtils.md5Hex(password) + sole);
    }

    public static String createSoleString() {
        return new BigInteger(80, random).toString(16);
    }
}
