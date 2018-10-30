package com.store.page.menu;

import com.store.factory.PageObjectFactory;
import com.store.page.WebElementManipulator;
import com.store.page.cart.CartPage;
import com.store.page.category.CategoryPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MenuPage extends WebElementManipulator<MenuPage> {

    private static final Logger LOG = Logger.getLogger(MenuPage.class);

    @FindBy(css = "#menu-item-33 .menu-item")
    private List<WebElement> productCategories;

    @FindBy(css = "#menu-item-33")
    private WebElement productCategoryButton;

    @FindBy(css = "#menu-item-34")
    private WebElement accessoriesCategoryButton;

    @FindBy(css = "#menu-item-35")
    private WebElement iMacsCategoryButton;

    @FindBy(css = "#header_cart")
    private WebElement cart;

    public MenuPage(WebDriver driver, WebDriverWait wait, Actions actions) {
        super(driver, wait, actions);
        PageFactory.initElements(driver, this);
    }

    public CategoryPage goToAccessoriesCategory() {
        hoverOverElement(productCategoryButton)
                .clickElement(accessoriesCategoryButton);
        return PageObjectFactory.createCategoryPage(driver);
    }

    public CartPage goToCartPage() {
        clickElement(cart);
        return PageObjectFactory.createCartPage(driver);
    }

    public CategoryPage goToRandomCategory() {
        int size = productCategories.size();
        int random = ThreadLocalRandom.current().nextInt(0, size);
        hoverOverElement(productCategoryButton)
                .clickElement(productCategories.get(random));
        return PageObjectFactory.createCategoryPage(driver);
    }

    @Override
    protected MenuPage getThis() {
        return this;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
