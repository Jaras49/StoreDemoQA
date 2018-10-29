package com.store.page.category;

import com.store.page.WebElementManipulator;
import com.store.page.menu.MenuPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CategoryPage extends WebElementManipulator<CategoryPage> {

    private MenuPage menu;

    @FindBy(css = "#content  h2 > a")
    private List<WebElement> products;

    public CategoryPage(WebDriver driver, WebDriverWait wait, Actions actions, MenuPage menuPage) {
        super(driver, wait, actions);
        this.menu = menuPage;
        PageFactory.initElements(driver, this);
    }

    @Override
    protected CategoryPage getThis() {
        return this;
    }

    public MenuPage getMenu() {
        return menu;
    }
}
