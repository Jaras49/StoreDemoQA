package com.store;

import com.factory.driver.DriverFactory;
import com.factory.driver.DriverType;
import com.store.factory.PageObjectFactory;
import com.store.page.menu.MenuPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

//TODO delete implicity wait
public abstract class BaseTest {

    private static final String DRIVER_NAME = "chromedriver.exe";
    private static final String STORE_URL = "http://store.demoqa.com/";

    protected WebDriver driver;
    protected MenuPage menu;

    @BeforeEach
    public void setUp() {
        driver = DriverFactory.getDriver(DriverType.CHROME);

        driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get(STORE_URL);

        menu = PageObjectFactory.createMenuPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        System.clearProperty("webdriver.chrome.driver");
    }
}
