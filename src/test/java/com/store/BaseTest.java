package com.store;

import com.factory.driver.DriverFactory;
import com.factory.driver.DriverNotFoundException;
import com.factory.driver.DriverType;
import com.store.factory.PageObjectFactory;
import com.store.page.menu.MenuPage;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public abstract class BaseTest {

    private static final String DRIVER_NAME = "drivers/chromedriver.exe";
    private static final String STORE_URL = "http://store.demoqa.com/";
    private static final String PROPERTIES_FILE_NAME = "test.properties";
    private static final String DRIVER_PROPERTY = "driver";
    private static final String SCREENSHOTS_FIR_PROPERTY = "dir.screenshots";

    protected WebDriver driver;
    protected MenuPage menu;
    private Properties properties;

    @BeforeEach
    public void setUp() throws IOException, DriverNotFoundException {
        properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
        DriverType driverType = DriverType.fromName(properties.getProperty(DRIVER_PROPERTY));

        driver = DriverFactory.getDriver(driverType);
        getLogger().info("INITIALIZING " + driverType.getName() + " DRIVER");

        driver.manage().window().maximize();
        driver.get(STORE_URL);
        getLogger().info("STARTING TESTS AT URL " + STORE_URL);

        menu = PageObjectFactory.createMenuPage(driver);
    }

    @AfterEach
    public void tearDown() throws IOException {
        getLogger().info("FINISHED TEST");
        getLogger().info("LAST URL " + driver.getCurrentUrl());
        takeScreenshot();

        driver.quit();
    }

    private void takeScreenshot() throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.move(screenshot.toPath(), Paths.get(properties.getProperty(SCREENSHOTS_FIR_PROPERTY) + screenshot.getName()));
        Files.deleteIfExists(screenshot.toPath());
    }

    protected abstract Logger getLogger();
}
