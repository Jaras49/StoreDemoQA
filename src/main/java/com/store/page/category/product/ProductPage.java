package com.store.page.category.product;

import com.store.page.WebElementManipulator;
import com.store.page.menu.MenuPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;

public class ProductPage extends WebElementManipulator<ProductPage> {

    private static final String REMOVE_DASHES_REGEX = "\\p{Pd}";

    private MenuPage menu;

    @FindBy(css = ".prodtitle")
    private WebElement productTitle;

    @FindBy(css = ".currentprice")
    private WebElement productPrice;

    @FindBy(css = "input[type='submit']")
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

    public ProductPage addProductXtimes(int timesToAddProduct) {
        for (int i = 0; i < timesToAddProduct; i++) {
            addProduct();
        }
        return this;
    }

    public String getProductName() {
        return productTitle.getText().replaceAll(REMOVE_DASHES_REGEX, "");
    }

    public BigDecimal getProductPrice() {
        String productPriceString = this.productPrice.getText().replaceAll("[$,]", "");
        return new BigDecimal(productPriceString);
    }

    public MenuPage getMenu() {
        return menu;
    }

    private void addProduct() {
        clickElementAndWaitToBeVisible(addToCartButton, productAddedAlert)
                .waitForElementTextUpdate(numberOfCartItems, expectedCartText());
    }

    private String expectedCartText() {
        int currentCartProducts = getNumberOfCartItems();
        int expectedCartProducts = currentCartProducts + 1;
        return String.valueOf(expectedCartProducts);
    }

    private int getNumberOfCartItems() {
        return Integer.parseInt(numberOfCartItems.getText());
    }
}
