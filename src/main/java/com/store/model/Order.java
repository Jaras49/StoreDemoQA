package com.store.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Order {

    private final List<Product> products;
    private BigDecimal shippingPrice;

    public Order(List<Product> products) {
        this.products = new ArrayList<>();
        this.shippingPrice = BigDecimal.ZERO;
        products.forEach(this::addProduct);
    }

    public void addProduct(Product product) {
        Optional<Product> optional = is(product.getProductName());
        if (optional.isPresent()) {
            optional.get().addProduct();
        } else {
            products.add(product);
        }
    }

    public BigDecimal getOrderPrice() {
        return products.stream()
                .map(Product::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getOrderPriceWithShipping() {
        return getOrderPrice().add(shippingPrice);
    }

    public List<Product> getProducts() {
        return products;
    }

    public BigDecimal getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(BigDecimal shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    private Optional<Product> is(String name) {
        return products.stream()
                .filter(product -> product.getProductName().equalsIgnoreCase(name))
                .findAny();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(products, order.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products);
    }

    @Override
    public String toString() {
        return "Order{" +
                "products=" + products +
                '}';
    }
}
