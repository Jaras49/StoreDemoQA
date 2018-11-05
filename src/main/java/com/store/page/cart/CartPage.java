package com.store.page.cart;

import com.annotations.WaitUntilVisible;
import com.store.factory.PageObjectFactory;
import com.store.model.Order;
import com.store.model.Product;
import com.store.page.WebElementManipulator;
import com.store.page.cart.checkout.CheckoutPage;
import com.store.page.menu.MenuPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends WebElementManipulator<CartPage> {

    private static final Logger LOG = Logger.getLogger(CartPage.class);

    private static final String REMOVE_DASHES_REGEX = "\\p{Pd}";
    private static final String PRODUCT_QUANTITY_SELECTOR = ".wpsc_product_quantity input[name='quantity']";
    private static final String PRODUCT_PRICE_SELECTOR = ".wpsc_product_quantity + td";
    private static final String PRODUCT_NAME_SELECTOR = ".wpsc_product_name";
    private static final String PRODUCT_TOTAL_PRICE_SELECTOR = ".wpsc_product_price";

    private MenuPage menu;

    @WaitUntilVisible
    @FindBy(css = ".yourtotal .pricedisplay")
    private WebElement totalCartPrice;

    @FindBy(css = ".checkout_cart tr.product_row")
    private List<WebElement> products;

    @WaitUntilVisible
    @FindBy(css = ".step2")
    private WebElement continueButton;

    public CartPage(WebDriver driver, WebDriverWait wait, Actions actions, MenuPage menuPage) {
        super(driver, wait, actions);
        this.menu = menuPage;
        PageFactory.initElements(driver, this);
        waitUntilPageLoads();
    }

    public MenuPage getMenu() {
        return menu;
    }

    public CheckoutPage clickContinueButton() {
        clickElement(continueButton);
        return PageObjectFactory.createCheckoutPage(driver);
    }

    public CartPage assertCart(Order expectedOrder) {
        Order actualOrder = mapTableRowsToObjects();
        return assertEquals(expectedOrder, actualOrder)
                .assertProductTotalPrices()
                .assertEquals(expectedOrder.getOrderPrice(), getTotalPrice());
    }
    private CartPage assertProductTotalPrices() {
        products.stream()
                .forEach(product -> {
                    int productQuantity = getProductQuantity(product);
                    BigDecimal productPrice = getProductPrice(product);
                    assertEquals(productPrice.multiply(BigDecimal.valueOf(productQuantity)), getProductTotalPrice(product));
                });
        return this;
    }

    private BigDecimal getTotalPrice() {
        return new BigDecimal(totalCartPrice.getText().replaceAll("[$,]", ""));
    }

    private BigDecimal getProductTotalPrice(WebElement product) {
        return new BigDecimal
                (product.findElement(By.cssSelector(PRODUCT_TOTAL_PRICE_SELECTOR)).getText().replaceAll("[$,]", ""));
    }

    private Order mapTableRowsToObjects() {
        return new Order(products.stream()
                .map(product -> {
                    String productName = getProductName(product);
                    BigDecimal price = getProductPrice(product);
                    int quantity = getProductQuantity(product);

                    List<Product> p = new ArrayList<>();

                    for (int i = 0; i < quantity; i++) {
                        p.add(new Product(productName, price));
                    }
                    return p;
                }).flatMap(Collection::stream)
                .collect(Collectors.toList()));
    }

    private int getProductQuantity(WebElement product) {
        String quantityString = product.findElement(By.cssSelector(PRODUCT_QUANTITY_SELECTOR)).getAttribute("value");
        return Integer.parseInt(quantityString);
    }

    private BigDecimal getProductPrice(WebElement product) {
        return new BigDecimal
                (product.findElement(By.cssSelector(PRODUCT_PRICE_SELECTOR)).getText().replaceAll("[$,]", ""));
    }

    private String getProductName(WebElement product) {
        return product.findElement(By.cssSelector(PRODUCT_NAME_SELECTOR)).getText().replaceAll(REMOVE_DASHES_REGEX, "");
    }

    @Override
    protected CartPage getThis() {
        return this;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
