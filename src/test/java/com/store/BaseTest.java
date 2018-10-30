package com.store;

import com.factory.driver.DriverFactory;
import com.factory.driver.DriverType;
import com.store.factory.PageObjectFactory;
import com.store.page.menu.MenuPage;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {

    private static final String DRIVER_NAME = "chromedriver.exe";
    private static final String STORE_URL = "http://store.demoqa.com/";

    protected WebDriver driver;
    protected MenuPage menu;

    @BeforeEach
    public void setUp() {
        driver = DriverFactory.getDriver(DriverType.CHROME);
        getLogger().info("INITIALIZING " + DRIVER_NAME + " DRIVER");

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
