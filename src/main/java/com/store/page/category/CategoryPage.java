package com.store.page.category;

import com.store.factory.PageObjectFactory;
import com.store.page.WebElementManipulator;
import com.store.page.category.product.ProductPage;
import com.store.page.menu.MenuPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CategoryPage extends WebElementManipulator<CategoryPage> {

    private static final String REAMOVE_DASHES_REGEX = "\\p{Pd}";
    private static final Logger LOG = Logger.getLogger(CategoryPage.class);

    private MenuPage menu;

    @FindBy(css = "#content  h2 > a")
    private List<WebElement> products;

    public CategoryPage(WebDriver driver, WebDriverWait wait, Actions actions, MenuPage menuPage) {
        super(driver, wait, actions);
        this.menu = menuPage;
        PageFactory.initElements(driver, this);
    }

    public ProductPage goToRandomProductPageAndAssertItSwitchedCorrectly() {
        int size = products.size();
        int random = ThreadLocalRandom.current().nextInt(0, size);

        WebElement element = products.get(random);
        String productName = element.getText().replaceAll(REAMOVE_DASHES_REGEX, "");

        clickElement(element);
        ProductPage productPage = PageObjectFactory.createProductPage(driver);

        assertEquals(productName, productPage.getProductName());
        return productPage;
    }

    public MenuPage getMenu() {
        return menu;
    }

    @Override
    protected CategoryPage getThis() {
        return this;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
