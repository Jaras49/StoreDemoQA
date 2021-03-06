package com.store.page.cart.checkout;

import com.annotations.WaitUntilVisible;
import com.store.factory.PageObjectFactory;
import com.store.model.Order;
import com.store.model.User;
import com.store.page.WebElementManipulator;
import com.store.page.cart.summary.TransactionSummaryPage;
import com.store.page.menu.MenuPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;

public class CheckoutPage extends WebElementManipulator<CheckoutPage> {

    private static final Logger LOG = Logger.getLogger(CheckoutPage.class);

    private MenuPage menu;

    @WaitUntilVisible
    @FindBy(css = "#wpsc_checkout_form_9")
    private WebElement emailInput;

    @WaitUntilVisible
    @FindBy(css = "#wpsc_checkout_form_2")
    private WebElement firstnameInput;

    @WaitUntilVisible
    @FindBy(css = "#wpsc_checkout_form_3")
    private WebElement lastnameInput;

    @WaitUntilVisible
    @FindBy(css = "#wpsc_checkout_form_4")
    private WebElement addressInput;

    @WaitUntilVisible
    @FindBy(css = "#wpsc_checkout_form_5")
    private WebElement cityInput;

    @WaitUntilVisible
    @FindBy(css = "#wpsc_checkout_form_6")
    private WebElement stateInput;

    @FindBy(css = "#current_country")
    private WebElement shippingCountrySelect;

    @FindBy(css = "#wpsc_checkout_form_7")
    private WebElement countrySelect;

    @WaitUntilVisible
    @FindBy(css = "#wpsc_checkout_form_18")
    private WebElement phoneInput;

    @WaitUntilVisible
    @FindBy(css = "#change_country input[type='submit']")
    private WebElement calculateShippingButton;

    @WaitUntilVisible
    @FindBy(css = "#shippingSameBilling")
    private WebElement sameAsBillingAddressCheckBox;

    @FindBy(css = ".table-2 tr:first-of-type")
    private WebElement shippingAddressContent;

    @FindBy(css = ".table-4")
    private WebElement priceSummaryTable;

    @FindBy(xpath = "//*[contains(text(), 'Total Shipping')]/following-sibling::*")
    private WebElement totalShippingCost;

    @FindBy(xpath = "//*[contains(text(), 'Item Cost')]/following-sibling::*")
    private WebElement itemCost;

    @FindBy(xpath = "//*[contains(text(), 'Total Price')]/following-sibling::*")
    private WebElement totalPrice;

    @WaitUntilVisible
    @FindBy(css = "input[value='Purchase']")
    private WebElement purchaseButton;

    public CheckoutPage(WebDriver driver, WebDriverWait wait, Actions actions, MenuPage menu) {
        super(driver, wait, actions);
        this.menu = menu;
        PageFactory.initElements(driver, this);
        waitUntilPageLoads();
    }

    public TransactionSummaryPage clickPurchaseButton() {
        clickElement(purchaseButton);
        return PageObjectFactory.createTransactionSummaryPage(driver);
    }

    public CheckoutPage assertCosts(Order expectedOrder) {
        return assertEquals(expectedOrder.getOrderPrice(), getItemCost())
                .assertEquals(expectedOrder.getOrderPriceWithShipping(), getTotalPrice());
    }

    public CheckoutPage fillFormWithUserDetails(User user) {
        return selectDropdownByVisibleText(shippingCountrySelect, user.getCountry())
                .clickElementAndWaitToBeVisible(calculateShippingButton, priceSummaryTable)
                .sendKeys(emailInput, user.getEmail())
                .sendKeys(firstnameInput, user.getFirstname())
                .sendKeys(lastnameInput, user.getLastname())
                .sendKeys(addressInput, user.getAddress())
                .sendKeys(cityInput, user.getCity())
                .sendKeys(stateInput, user.getState())
                .selectDropdownByVisibleText(countrySelect, user.getCountry())
                .sendKeys(phoneInput, user.getPhone())
                .clickElementAndWaitToBeInvisible(sameAsBillingAddressCheckBox, shippingAddressContent);
    }

    public BigDecimal getShippingCost() {
        return new BigDecimal(totalShippingCost.getText().replaceAll("[$,]", ""));
    }

    public MenuPage getMenu() {
        return menu;
    }

    private BigDecimal getItemCost() {
        return new BigDecimal(itemCost.getText().replaceAll("[$,]", ""));
    }

    private BigDecimal getTotalPrice() {
        return new BigDecimal(totalPrice.getText().replaceAll("[$,]", ""));
    }

    @Override
    protected CheckoutPage getThis() {
        return this;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
