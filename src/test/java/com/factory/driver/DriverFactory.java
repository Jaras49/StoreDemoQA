package com.factory.driver;

import com.properties.TestProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static final String CHROME_DRIVER_NAME = "drivers/chromedriver.exe";
    private static final String FIREFOX_DRIVER_NAEME = "drivers/geckodriver.exe";
    private static final String EDGE_DRIVER_NAME = "drivers/MicrosoftWebDriver.exe";
    private static final String GRID_IP_PROPERTY = "grid.hub.ip";

    public static WebDriver getDriver(DriverType driverType) throws DriverNotFoundException, MalformedURLException {
        String driverPath;
        String gridAddress;
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
            case GRID_CHROME:
                gridAddress = TestProperties.getProperty(GRID_IP_PROPERTY);

                return new RemoteWebDriver(new URL(gridAddress), new ChromeOptions());
            case GRID_FIREFOX:
                gridAddress = TestProperties.getProperty(GRID_IP_PROPERTY);

                return new RemoteWebDriver(new URL(gridAddress), new FirefoxOptions());
        }
        throw new DriverNotFoundException(DriverNotFoundException.NO_DRIVER_TYPE);
    }
}
