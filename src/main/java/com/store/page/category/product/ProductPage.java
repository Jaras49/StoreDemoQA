package com.store.page.category.product;

import com.annotations.WaitUntilVisible;
import com.store.page.WebElementManipulator;
import com.store.page.menu.MenuPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;

public class ProductPage extends WebElementManipulator<ProductPage> {

    private static final Logger LOG = Logger.getLogger(ProductPage.class);

    private static final String REMOVE_DASHES_REGEX = "\\p{Pd}";

    private MenuPage menu;

    @WaitUntilVisible
    @FindBy(css = ".prodtitle")
    private WebElement productTitle;

    @WaitUntilVisible
    @FindBy(css = ".currentprice")
    private WebElement productPrice;

    @WaitUntilVisible
    @FindBy(css = "input[type='submit']")
    private WebElement addToCartButton;

    @FindBy(css = ".alert.addtocart")
    private WebElement productAddedAlert;

    public ProductPage(WebDriver driver, WebDriverWait wait, Actions actions, MenuPage menuPage) {
        super(driver, wait, actions);
        this.menu = menuPage;
        PageFactory.initElements(driver, this);
        waitUntilPageLoads();
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
                .waitForElementTextUpdate(menu.getNumberOfCartItemsWebElement(), expectedCartText());
    }

    private String expectedCartText() {
        int currentCartProducts = menu.getNumberOfCartItems();
        int expectedCartProducts = currentCartProducts + 1;
        return String.valueOf(expectedCartProducts);
    }

    @Override
    protected ProductPage getThis() {
        return this;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
