package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;

public class Product {
    private String name;
    private BigDecimal price;
    private Category category;

    public Product(String name, BigDecimal price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (price != null ? !price.equals(product.price) : product.price != null) return false;
        return category != null ? category.equals(product.category) : product.category == null;
    }

    public String getName() {
        return name;
    }


    public BigDecimal getPrice() {
        return price;
    }


    public Category getCategory() {
        return category;
    }

}
