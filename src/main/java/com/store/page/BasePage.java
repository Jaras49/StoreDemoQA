package com.store.page;

import com.annotations.WaitUntilVisible;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePage<T extends BasePage> {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    BasePage(WebDriver driver, WebDriverWait wait, Actions actions) {
        this.driver = driver;
        this.wait = wait;
        this.actions = actions;
    }

    protected abstract Logger getLogger();

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
        getLogger().info("Waiting for element to become visible - " + convertElementToText(element));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementToBeInvisible(WebElement element) {
        getLogger().info("Waiting for element to become invisible - " + convertElementToText(element));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected T waitForElementTextUpdate(WebElement element, String textToBe) {
        getLogger().info("Waiting for element - " + convertElementToText(element) + " to " + textToBe);
        wait.until(ExpectedConditions.textToBePresentInElement(element, textToBe));
        return getThis();
    }

    protected void waitUntilPageLoads() {
        wait.until(ExpectedConditions.visibilityOfAllElements(getFieldsAnnotatedWithWaitForVisible()));
    }

    protected String convertElementToText(WebElement element) {
        return StringUtils.substringAfterLast(element.toString(), "->").trim();
    }

    private List<WebElement> getFieldsAnnotatedWithWaitForVisible() {
        List<WebElement> result = new ArrayList<>();

        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (declaredField.getType() == WebElement.class && declaredField.isAnnotationPresent(WaitUntilVisible.class)) {
                try {
                    result.add((WebElement) declaredField.get(this));
                } catch (IllegalAccessException e) {
                    getLogger().error("ILLEGAL ACCESS TO FIELD", e);
                } finally {
                    declaredField.setAccessible(false);
                }
            }
        }
        return result;
    }
}
