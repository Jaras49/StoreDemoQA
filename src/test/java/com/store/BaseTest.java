package com.store;

import com.factory.driver.DriverFactory;
import com.factory.driver.DriverNotFoundException;
import com.factory.driver.DriverType;
import com.properties.TestProperties;
import com.store.factory.PageObjectFactory;
import com.store.page.menu.MenuPage;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class BaseTest {

    private static final String STORE_URL = "http://store.demoqa.com/";
    private static final String DRIVER_PROPERTY = "driver";
    private static final String SCREENSHOTS_DIR_PROPERTY = "dir.screenshots";

    protected WebDriver driver;
    protected MenuPage menu;

    @BeforeEach
    public void setUp() throws IOException, DriverNotFoundException {
        DriverType driverType = DriverType.fromName(TestProperties.getProperty(DRIVER_PROPERTY));

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
        printBrowserLogs();
        takeScreenshot();

        driver.quit();
    }

    private void printBrowserLogs() {
        try {
            getLogger().debug("BROWSER LOGS:");
            LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
            logEntries.forEach(n -> getLogger().info(n));
        } catch (UnsupportedCommandException ex) {
            getLogger().warn("GET BROWSER LOGS COMMAND IS NOT SUPPORTED BY CURRENT DRIVER");
        }
    }

    private void takeScreenshot() throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.move(screenshot.toPath(), Paths.get(TestProperties.getProperty(SCREENSHOTS_DIR_PROPERTY) + screenshot.getName()));
        Files.deleteIfExists(screenshot.toPath());
    }

    protected abstract Logger getLogger();
}
