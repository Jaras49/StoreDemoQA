package com.store.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Product implements Comparable<Product> {

    private final String productName;
    private final BigDecimal price;
    private int quantity;
    private BigDecimal totalPrice;

    public Product(String productName, BigDecimal price) {
        this.productName = productName;
        this.price = price;
        quantity++;
        totalPrice = price.add(BigDecimal.ZERO);
    }

    void addProduct() {
        quantity++;
        totalPrice = totalPrice.add(price);
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return quantity == product.quantity &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(price, product.price) &&
                Objects.equals(totalPrice, product.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, price, quantity, totalPrice);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }

    @Override
    public int compareTo(Product o) {
        return this.productName.compareToIgnoreCase(o.getProductName());
    }
}
