package com.store.page.cart.checkout;

import com.store.page.WebElementManipulator;
import com.store.page.menu.MenuPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends WebElementManipulator<CheckoutPage> {

    private MenuPage menu;

    @FindBy(css = "#wpsc_checkout_form_2")
    private WebElement firstnameInput;

    @FindBy(css = "#wpsc_checkout_form_3")
    private WebElement lastnameInput;

    @FindBy(css = "#wpsc_checkout_form_4")
    private WebElement addressInput;

    @FindBy(css = "#wpsc_checkout_form_5")
    private WebElement cityInput;

    @FindBy(css = "#wpsc_checkout_form_6")
    private WebElement stateInput;

    @FindBy(css = "#wpsc_checkout_form_7")
    private WebElement countrySelect;

    @FindBy(css = "#wpsc_checkout_form_18")
    private WebElement phoneInput;

    @FindBy(css = "#shippingSameBilling")
    private WebElement sameAsBillingAddressCheckBox;

    @FindBy(xpath = "//*[contains(text(), 'Total Shipping')]/following-sibling::*")
    private WebElement totalShippingCost;

    @FindBy(xpath = "//*[contains(text(), 'Item Cost')]/following-sibling::*")
    private WebElement itemCost;

    @FindBy(xpath = "//*[contains(text(), 'Total Price')]/following-sibling::*")
    private WebElement totalPrice;

    public CheckoutPage(WebDriver driver, WebDriverWait wait, Actions actions, MenuPage menu) {
        super(driver, wait, actions);
        this.menu = menu;
        PageFactory.initElements(driver, this);
    }

    @Override
    protected CheckoutPage getThis() {
        return this;
    }

    public MenuPage getMenu() {
        return menu;
    }
}
