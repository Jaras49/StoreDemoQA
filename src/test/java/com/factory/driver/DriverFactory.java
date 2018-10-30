package com.factory.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    private static final String CHROME_DRIVER_NAME = "drivers/chromedriver.exe";
    private static final String FIREFOX_DRIVER_NAEME = "drivers/geckodriver.exe";
    private static final String EDGE_DRIVER_NAME = "drivers/MicrosoftWebDriver.exe";

    public static WebDriver getDriver(DriverType driverType) throws DriverNotFoundException {
        String driverPath;
        switch (driverType) {
            case CHROME:
                driverPath = DriverFactory.class.getClassLoader().getResource(CHROME_DRIVER_NAME).getPath();
                System.setProperty("webdriver.chrome.driver", driverPath);

                return new ChromeDriver();
            case FIREFOX:
                driverPath = DriverFactory.class.getClassLoader().getResource(FIREFOX_DRIVER_NAEME).getPath();
                System.setProperty("webdriver.gecko.driver", driverPath);

                return new FirefoxDriver();
            case EDGE:
                driverPath = DriverFactory.class.getClassLoader().getResource(EDGE_DRIVER_NAME).getPath();
                System.setProperty("webdriver.edge.driver", driverPath);

                return new EdgeDriver();
        }
        throw new DriverNotFoundException(DriverNotFoundException.NO_DRIVER_TYPE);
    }
}
