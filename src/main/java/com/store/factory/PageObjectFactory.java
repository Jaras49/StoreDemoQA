package com.store.factory;

import com.store.page.category.CategoryPage;
import com.store.page.category.product.ProductPage;
import com.store.page.menu.MenuPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class PageObjectFactory {

    private static final long DEFAULT_WAIT = 15;

    public static ProductPage createProductPage(WebDriver driver) {
        return new ProductPage
                (driver, new WebDriverWait(driver, DEFAULT_WAIT), new Actions(driver), createMenuPage(driver));
    }

    public static CategoryPage createCategoryPage(WebDriver driver) {
        return new CategoryPage
                (driver, new WebDriverWait(driver, DEFAULT_WAIT), new Actions(driver), createMenuPage(driver));
    }

    public static MenuPage createMenuPage(WebDriver driver) {
        return new MenuPage
                (driver, new WebDriverWait(driver, DEFAULT_WAIT), new Actions(driver));
    }
}
