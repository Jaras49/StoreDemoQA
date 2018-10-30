package com.store.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Product implements Comparable<Product> {

    private static final String REPLACE_ALL_DASHES_REGEX = "\\p{Pd}";

    private final String productName;
    private final BigDecimal price;

    public Product(String productName, BigDecimal price) {
        this.productName = productName;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;

        return Objects.equals(
                productName.replaceAll(REPLACE_ALL_DASHES_REGEX, ""),
                product.productName.replaceAll(REPLACE_ALL_DASHES_REGEX, "")) &&
                Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(Product o) {
        return this.productName.compareToIgnoreCase(o.getProductName());
    }
}
