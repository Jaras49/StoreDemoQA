package com.store.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    protected T clickElement(WebElement element) {
        LOG.info("Clicking element " + element.getTagName());
        element.click();
        return getThis();
    }
}