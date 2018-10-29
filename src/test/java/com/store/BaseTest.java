package com.store;

import com.factory.driver.DriverFactory;
import com.factory.driver.DriverType;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {

    private static final String DRIVER_NAME = "chromedriver.exe";
    private static final String STORE_URL = "http://store.demoqa.com/";

    protected WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverFactory.getDriver(DriverType.CHROME);

        driver.manage().window().maximize();
        driver.get(STORE_URL);
    }

    @After
    public void tearDown() {
        driver.quit();
        System.clearProperty("webdriver.chrome.driver");
    }
}
