package com.store.checkout;

import com.store.BaseTest;
import com.store.model.Order;
import com.store.model.Product;
import com.store.page.cart.CartPage;
import com.store.page.category.product.ProductPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
//TODO
public class CheckoutTest extends BaseTest {

    private Order order;

    @Test
    public void shouldCheckout() {
        order = new Order(new ArrayList<>());
        for (int i = 0; i < 4; i++) {
            dooo();
        }
        CartPage cartPage = menu.goToCartPage()
                .assertProductTotalPrices();

        Order cartOrder = new Order(
                cartPage.mapTableRowsToObjects().getProducts());

        Assertions.assertTrue(order.getProducts().containsAll(cartOrder.getProducts()) && cartOrder.getProducts().containsAll(order.getProducts()));
        Assertions.assertEquals(order.getOrderPrice(), cartPage.getTotalPrice());

        cartPage.clickContinueButton();
    }

    private void dooo() {
        int random = ThreadLocalRandom.current().nextInt(1, 5);

        ProductPage productPage = menu.goToRandomCategory()
                .goToRandomProductPageAndAssertItSwitchedCorrectly();
        addToOrder(productPage.getProductName(), productPage.getProductPrice(), random);

        productPage.addProductXtimes(random);
    }

    private void addToOrder(String productName, BigDecimal price, int quantity) {

        for (int i = 0; i < quantity; i++) {
            order.addProduct(new Product(productName, price));
        }
    }
}
