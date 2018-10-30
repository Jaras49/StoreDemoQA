package com.store.page.cart;

import com.store.model.Order;
import com.store.model.Product;
import com.store.page.WebElementManipulator;
import com.store.page.menu.MenuPage;
import org.junit.jupiter.api.Assertions;
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

    private static final String PRODUCT_QUANTITY_SELECTOR = ".wpsc_product_quantity input[name='quantity']";
    private static final String PRODUCT_PRICE_SELECTOR = ".wpsc_product_quantity + td .pricedisplay";
    private static final String PRODUCT_NAME_SELECTOR = ".wpsc_product_name a";
    private static final String PRODUCT_TOTAL_PRICE_SELECTOR = ".wpsc_product_price .pricedisplay .pricedisplay";

    private MenuPage menu;

    @FindBy(css = ".yourtotal .pricedisplay")
    private WebElement totalCartPrice;

    @FindBy(css = ".checkout_cart tr.product_row")
    private List<WebElement> products;

    public CartPage(WebDriver driver, WebDriverWait wait, Actions actions, MenuPage menuPage) {
        super(driver, wait, actions);
        this.menu = menuPage;
        PageFactory.initElements(driver, this);
    }

    @Override
    protected CartPage getThis() {
        return this;
    }

    public CartPage assertProductTotalPrices() {
        products.stream()
                .forEach(product -> {
                    int productQuantity = getProductQuantity(product);
                    BigDecimal productPrice = getProductPrice(product);
                    Assertions.assertEquals(productPrice.multiply(BigDecimal.valueOf(productQuantity)), getProductTotalPrice(product));
                });
        return this;
    }

    public Order mapTableRowsToObjects() {
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

    public BigDecimal getTotalPrice() {
        return new BigDecimal(totalCartPrice.getText().replaceAll("[$,]", ""));
    }

    public BigDecimal getProductTotalPrice(WebElement product) {
        return new BigDecimal
                (product.findElement(By.cssSelector(PRODUCT_TOTAL_PRICE_SELECTOR)).getText().replaceAll("[$,]", ""));
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
        return product.findElement(By.cssSelector(PRODUCT_NAME_SELECTOR)).getText();
    }

    public MenuPage getMenu() {
        return menu;
    }
}
