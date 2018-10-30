package com.store.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

//TODO LOGGER
public abstract class WebElementManipulator<T extends WebElementManipulator> extends BasePage<T> {

    private static Logger LOG = Logger.getLogger(WebElementManipulator.class.getName());


    protected WebElementManipulator(WebDriver driver, WebDriverWait wait, Actions actions) {
        super(driver, wait, actions);
    }

    protected T hoverOverElement(WebElement element) {
        LOG.info("Hovering over element " + element.getTagName());
        actions.moveToElement(element).perform();
        return getThis();
    }

    protected T sendKeys(WebElement element, String keysToSend) {
        element.sendKeys(keysToSend);
        return getThis();
    }

    protected T selectDropdownByVisibleText(WebElement selectElement, String text) throws InterruptedException {
        new Select(selectElement).selectByVisibleText(text);
        return getThis();
    }

    protected T clickElementAndWaitToBeVisible(WebElement toClick, WebElement toBeVisible) {
        LOG.info("Clicking element " + toClick.getTagName());
        toClick.click();
        LOG.info("Waiting for element to become visible " + toBeVisible.getTagName());
        waitForElementToBeVisible(toBeVisible);
        return getThis();
    }

    protected T clickElementAndWaitToBeInvisible(WebElement toClick, WebElement toBeInvisible) {
        toClick.click();
        waitForElementToBeInvisible(toBeInvisible);
        return getThis();
    }

    protected T clickElement(WebElement element) {
        LOG.info("Clicking element " + element.getTagName());
        element.click();
        return getThis();
    }
}