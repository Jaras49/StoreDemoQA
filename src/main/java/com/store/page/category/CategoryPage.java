package com.store.page.category;

import com.store.factory.PageObjectFactory;
import com.store.page.WebElementManipulator;
import com.store.page.category.product.ProductPage;
import com.store.page.menu.MenuPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CategoryPage extends WebElementManipulator<CategoryPage> {

    private MenuPage menu;

    @FindBy(css = "#content  h2 > a")
    private List<WebElement> products;

    public CategoryPage(WebDriver driver, WebDriverWait wait, Actions actions, MenuPage menuPage) {
        super(driver, wait, actions);
        this.menu = menuPage;
        PageFactory.initElements(driver, this);
    }

    @Override
    protected CategoryPage getThis() {
        return this;
    }

    public ProductPage goToRandomProductPageAndAssertItSwitchedCorrectly() {
        int size = products.size();
        int random = ThreadLocalRandom.current().nextInt(0, size);

        WebElement element = products.get(random);
        String productName = element.getText();
        clickElement(element);
        ProductPage productPage = PageObjectFactory.createProductPage(driver);
        Assertions.assertEquals(productName, productPage.getProductName());

        return productPage;
    }

    public MenuPage getMenu() {
        return menu;
    }
}
