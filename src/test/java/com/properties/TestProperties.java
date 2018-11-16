package com.properties;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class TestProperties {

    private static final Logger LOG = Logger.getLogger(TestProperties.class);

    private static final String PROPERTIES_FILE_NAME = "test.properties";
    private static Properties INSTANCE;

    public static synchronized String getProperty(String propertyName) {
        if (INSTANCE == null) {
            INSTANCE = new Properties();
        }
        try {
            INSTANCE.load(TestProperties.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
        } catch (IOException ex) {
            LOG.error("FAILED TO LOAD TEST PROPERTIES FILE", ex);
        }
        return INSTANCE.getProperty(propertyName);
    }
}
