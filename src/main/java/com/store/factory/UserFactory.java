package com.store.factory;

import com.store.model.User;
import org.apache.commons.lang.RandomStringUtils;

public class UserFactory {

    private static final int LENGHT = 10;

    private UserFactory() {
    }

    public static User createRandomUser() {
        return new User
                (generateRandomString(LENGHT) + "@test.com",
                        "firstname" + generateRandomString(LENGHT),
                        "lastname" + generateRandomString(LENGHT),
                        "address" + generateRandomString(LENGHT),
                        "city" + generateRandomString(LENGHT),
                        "state" + generateRandomString(LENGHT),
                        "country" + generateRandomString(LENGHT),
                        generateRandomNumber(LENGHT));
    }

    private static String generateRandomString(int lenght) {
        return RandomStringUtils.randomAlphanumeric(lenght);
    }

    private static String generateRandomNumber(int lenght) {
        return RandomStringUtils.randomNumeric(lenght);
    }
}
