package com.store;

import com.factory.driver.DriverFactory;
import com.factory.driver.DriverNotFoundException;
import com.factory.driver.DriverType;
import com.store.factory.PageObjectFactory;
import com.store.page.menu.MenuPage;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Properties;

public abstract class BaseTest {

    private static final String DRIVER_NAME = "drivers/chromedriver.exe";
    private static final String STORE_URL = "http://store.demoqa.com/";
    private static final String PROPERTIES_FILE_NAME = "test.properties";
    private static final String DRIVER_PROPERTY = "driver";

    protected WebDriver driver;
    protected MenuPage menu;

    @BeforeEach
    public void setUp() throws IOException, DriverNotFoundException {
        Properties p = new Properties();
        p.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
        DriverType driverType = DriverType.fromName(p.getProperty(DRIVER_PROPERTY));

        driver = DriverFactory.getDriver(driverType);
        getLogger().info("INITIALIZING " + driverType.getName() + " DRIVER");

        driver.manage().window().maximize();
        driver.get(STORE_URL);
        getLogger().info("STARTING TESTS AT URL " + STORE_URL);

        menu = PageObjectFactory.createMenuPage(driver);
    }

    @AfterEach
    public void tearDown() {
        getLogger().info("FINISHED TEST");
        getLogger().info("LAST URL " + driver.getCurrentUrl());
        driver.quit();
    }

    protected abstract Logger getLogger();
}
