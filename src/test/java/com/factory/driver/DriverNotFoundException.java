package com.factory.driver;

public class DriverNotFoundException extends Exception {

    public static final String NO_DRIVER = "No driver found for specified name";
    public static final String NO_DRIVER_TYPE = "No driver found for specified DRIVER TYPE ";

    DriverNotFoundException(String message) {
        super(message);
    }
}
