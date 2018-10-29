package com.factory.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {

    private static final String CHROME_DRIVER_NAME = "chromedriver.exe";

    public static WebDriver getDriver(DriverType driverType) {

        if (driverType.name().equals("CHROME")) {

            String driverPath = DriverFactory.class.getClassLoader().getResource(CHROME_DRIVER_NAME).getPath();
            System.setProperty("webdriver.chrome.driver", driverPath);

            return new ChromeDriver();
        }
        return null;
    }
}
