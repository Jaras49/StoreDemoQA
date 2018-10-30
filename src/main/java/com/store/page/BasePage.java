package com.store.page;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage<T extends BasePage> {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    BasePage(WebDriver driver, WebDriverWait wait, Actions actions) {
        this.driver = driver;
        this.wait = wait;
        this.actions = actions;
    }

    protected abstract T getThis();

    public <M> T assertEquals(M expected, M actual) {
        Assertions.assertEquals(expected, actual);
        return getThis();
    }

    public T assertTrue(boolean actual) {
        Assertions.assertTrue(actual);
        return getThis();
    }

    public T assertFalse(boolean actual) {
        Assertions.assertFalse(actual);
        return getThis();
    }

    protected WebElement waitForElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementToBeInvisible(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected T waitForElementTextUpdate(WebElement element, String textToBe) {
        wait.until(ExpectedConditions.textToBePresentInElement(element, textToBe));
        return getThis();
    }
}
