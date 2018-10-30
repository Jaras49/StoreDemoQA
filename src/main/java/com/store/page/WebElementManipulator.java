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
        getLogger().info("Hovering to element - " + element.getText());
        actions.moveToElement(element).perform();
        return getThis();
    }

    protected T sendKeys(WebElement element, String keysToSend) {
        getLogger().info("Sending keys: " +  keysToSend + " to element - " + element.getText());
        element.sendKeys(keysToSend);
        return getThis();
    }

    protected T selectDropdownByVisibleText(WebElement selectElement, String text) {
        getLogger().info("Selecting dropdown by visible text: " + text + " in element " + selectElement.getText());
        new Select(selectElement).selectByVisibleText(text);
        return getThis();
    }

    protected T clickElementAndWaitToBeVisible(WebElement toClick, WebElement toBeVisible) {
        getLogger().info("Clicking element - " + toClick.getText());
        toClick.click();
        getLogger().info("Waiting for element to become visible - " + toBeVisible.getText());
        waitForElementToBeVisible(toBeVisible);
        return getThis();
    }

    protected T clickElementAndWaitToBeInvisible(WebElement toClick, WebElement toBeInvisible) {
        getLogger().info("Clicking element - " + toClick.getText());
        toClick.click();
        getLogger().info("Waiting for element to become invisible - " + toBeInvisible.getText());
        waitForElementToBeInvisible(toBeInvisible);
        return getThis();
    }

    protected T clickElement(WebElement element) {
        getLogger().info("Clicking element - "  + element.getText());
        element.click();
        return getThis();
    }
}