package com.store.page.category.product;

import com.store.page.WebElementManipulator;
import com.store.page.menu.MenuPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends WebElementManipulator<ProductPage> {

    private MenuPage menu;

    @FindBy(css = ".prodtitle")
    private WebElement productTitle;

    @FindBy(css = ".currentprice")
    private WebElement productPrice;

    @FindBy(css = "input[tyle='submit']")
    private WebElement addToCartButton;

    @FindBy(css = ".alert.addtocart")
    private WebElement productAddedAlert;

    @FindBy(css = "#header_cart .count")
    private WebElement numberOfCartItems;

    public ProductPage(WebDriver driver, WebDriverWait wait, Actions actions, MenuPage menuPage) {
        super(driver, wait, actions);
        this.menu = menuPage;
        PageFactory.initElements(driver, this);
    }

    @Override
    protected ProductPage getThis() {
        return this;
    }

    public MenuPage getMenu() {
        return menu;
    }
}
