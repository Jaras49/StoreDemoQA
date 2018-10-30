package com.store.page.cart.summary;

import com.store.page.WebElementManipulator;
import com.store.page.menu.MenuPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TransactionSummaryPage extends WebElementManipulator<TransactionSummaryPage> {

    private MenuPage menu;

    public TransactionSummaryPage(WebDriver driver, WebDriverWait wait, Actions actions, MenuPage menuPage) {
        super(driver, wait, actions);
        this.menu = menuPage;
        PageFactory.initElements(driver,this );
    }

    @Override
    protected TransactionSummaryPage getThis() {
        return this;
    }
}
