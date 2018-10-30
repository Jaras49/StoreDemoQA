package com.store.page.cart.summary;

import com.store.model.Order;
import com.store.model.Product;
import com.store.page.WebElementManipulator;
import com.store.page.menu.MenuPage;
import org.apache.commons.lang.StringUtils;
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

public class TransactionSummaryPage extends WebElementManipulator<TransactionSummaryPage> {

    private static final Logger LOG = Logger.getLogger(TransactionSummaryPage.class);

    private static final String REPLACE_DASHES_REGEX = "\\p{Pd}";
    private static final String PRODUCT_NAME_SELECTOR = "td:first-of-type";
    private static final String PRODUCT_PRICE_SELECTOR = "td:nth-of-type(2)";
    private static final String PRODUCT_QUANTITY_SELECTOR = "td:nth-of-type(3)";
    private static final String PRODUCT_TOTAL_PRICE_SELECTOR = "td:nth-of-type(4)";

    private MenuPage menu;

    @FindBy(css = ".wpsc-purchase-log-transaction-results > tbody > tr")
    private List<WebElement> productsTableRows;

    @FindBy(xpath = "//p[contains(text(), 'Total Shipping')]")
    private WebElement totalShippingPrice;

    public TransactionSummaryPage(WebDriver driver, WebDriverWait wait, Actions actions, MenuPage menuPage) {
        super(driver, wait, actions);
        this.menu = menuPage;
        PageFactory.initElements(driver, this);
    }

    public TransactionSummaryPage assertTransactionSummaryPage(Order expectedOrder) {
        Order actualOrder = mapSummaryTableToObjectAndAssertTotalPrices()
                .setShippingPrice(getTotalShippingPrice());

        return assertEquals(expectedOrder, actualOrder)
                .assertEquals(expectedOrder.getShippingPrice(), getTotalShippingPrice())
                .assertEquals(expectedOrder.getOrderPriceWithShipping(), getTotalPrice());
    }

    private Order mapSummaryTableToObjectAndAssertTotalPrices() {
        return new Order(productsTableRows.stream()
                .map(row -> {
                    String productName = row.findElement(By.cssSelector(PRODUCT_NAME_SELECTOR)).getText().replaceAll(REPLACE_DASHES_REGEX, "");
                    BigDecimal price = getProductPrice(row);
                    int quantity = Integer.parseInt(row.findElement(By.cssSelector(PRODUCT_QUANTITY_SELECTOR)).getText());

                    assertTotalPrice(row, price, quantity);

                    List<Product> p = new ArrayList<>();

                    for (int i = 0; i < quantity; i++) {
                        p.add(new Product(productName, price));
                    }
                    return p;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
    }

    private BigDecimal getTotalProductPrice(WebElement element) {
        return new BigDecimal
                (element.findElement(By.cssSelector(PRODUCT_TOTAL_PRICE_SELECTOR)).getText().replaceAll("[$,]", ""));
    }

    private BigDecimal getProductPrice(WebElement element) {
        return new BigDecimal
                (element.findElement(By.cssSelector(PRODUCT_PRICE_SELECTOR)).getText().replaceAll("[$,]", ""));
    }

    private void assertTotalPrice(WebElement product, BigDecimal price, int quantity) {
        assertEquals(price.multiply(BigDecimal.valueOf(quantity)), getTotalProductPrice(product));
    }

    private BigDecimal getTotalShippingPrice() {
        String totalShipping = StringUtils.substringBetween(totalShippingPrice.getText().replaceAll("[$,]", ""), ":", "\n");
        return new BigDecimal(totalShipping.trim());
    }

    private BigDecimal getTotalPrice() {
        String totalPrice = StringUtils.substringAfterLast(totalShippingPrice.getText().replaceAll("[$,]", ""), ":");
        return new BigDecimal(totalPrice.trim());
    }

    @Override
    protected TransactionSummaryPage getThis() {
        return this;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
