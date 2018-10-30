package com.store.checkout;

import com.store.BaseTest;
import com.store.factory.UserFactory;
import com.store.model.Order;
import com.store.model.Product;
import com.store.page.cart.checkout.CheckoutPage;
import com.store.page.category.product.ProductPage;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CheckoutTest extends BaseTest {

    private Order expectedOrder;

    @Test
    public void shouldCheckout() {
        expectedOrder = new Order(new ArrayList<>());

        for (int i = 0; i < 4; i++) {
            addRandomProducts();
        }
        CheckoutPage checkoutPage = menu.goToCartPage()
                .assertCart(expectedOrder)
                .clickContinueButton()
                .fillFormWithUserDetails(UserFactory.createRandomUser());

        expectedOrder.setShippingPrice(checkoutPage.getShippingCost());
        checkoutPage.assertCosts(expectedOrder)
                .clickPurchaseButton()
                .assertTransactionSummaryPage(expectedOrder);
    }

    private ProductPage addRandomProducts() {
        int random = ThreadLocalRandom.current().nextInt(1, 5);

        ProductPage productPage = menu.goToRandomCategory()
                .goToRandomProductPageAndAssertItSwitchedCorrectly();
        addToExpectedOrder(productPage.getProductName(), productPage.getProductPrice(), random);

        return productPage.addProductXtimes(random);
    }

    private void addToExpectedOrder(String productName, BigDecimal price, int quantity) {

        for (int i = 0; i < quantity; i++) {
            expectedOrder.addProduct(new Product(productName, price));
        }
    }
}
