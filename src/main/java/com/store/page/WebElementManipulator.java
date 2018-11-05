package com.store.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class WebElementManipulator<T extends WebElementManipulator> extends BasePage<T> {

    protected WebElementManipulator(WebDriver driver, WebDriverWait wait, Actions actions) {
        super(driver, wait, actions);
    }

    protected T hoverOverElement(WebElement element) {
        getLogger().info("Hovering to element - " + convertElementToText(element));
        actions.moveToElement(element).perform();
        return getThis();
    }

    protected T sendKeys(WebElement element, String keysToSend) {
        getLogger().info("Sending keys: " +  keysToSend + " to element - " + convertElementToText(element));
        element.sendKeys(keysToSend);
        return getThis();
    }

    protected T selectDropdownByVisibleText(WebElement selectElement, String text) {
        getLogger().info("Selecting dropdown by visible text: " + text + " in element " + convertElementToText(selectElement));
        new Select(selectElement).selectByVisibleText(text);
        return getThis();
    }

    protected T clickElementAndWaitToBeVisible(WebElement toClick, WebElement toBeVisible) {
        getLogger().info("Clicking element - " + convertElementToText(toClick));
        toClick.click();
        waitForElementToBeVisible(toBeVisible);
        return getThis();
    }

    protected T clickElementAndWaitToBeInvisible(WebElement toClick, WebElement toBeInvisible) {
        getLogger().info("Clicking element - " + convertElementToText(toClick));
        toClick.click();
        waitForElementToBeInvisible(toBeInvisible);
        return getThis();
    }

    protected T clickElement(WebElement element) {
        getLogger().info("Clicking element - "  + convertElementToText(element));
        element.click();
        return getThis();
    }
}