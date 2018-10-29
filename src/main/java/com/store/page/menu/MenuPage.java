package com.store.page.menu;

import com.store.page.WebElementManipulator;
import com.store.page.category.CategoryPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MenuPage extends WebElementManipulator<MenuPage> {

    @FindBy(css = "#menu-item-33 .menu-item")
    private List<WebElement> productCategories;

    @FindBy(css = "#menu-item-33")
    private WebElement productCategoryButton;

    @FindBy(css = "#menu-item-34")
    private WebElement accessoriesCategoryButton;

    @FindBy(css = "#menu-item-35")
    private WebElement iMacsCategoryButton;

    public MenuPage(WebDriver driver, WebDriverWait wait, Actions actions) {
        super(driver, wait, actions);
        PageFactory.initElements(driver, this);
    }

    @Override
    protected MenuPage getThis() {
        return this;
    }

    public CategoryPage goToAccessoriesCategory() {
        hoverOverElement(productCategoryButton)
                .clickElement(accessoriesCategoryButton);
        return new CategoryPage(driver, new WebDriverWait(driver, 15), new Actions(driver));
    }
}
