package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

public class OrderItem {
    private Product product;
    private int quantity;
    private BigDecimal taxedAmount;
    private BigDecimal tax;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem orderItem = (OrderItem) o;

        if (quantity != orderItem.quantity) return false;
        if (product != null ? !product.equals(orderItem.product) : orderItem.product != null) return false;
        if (taxedAmount != null ? !taxedAmount.equals(orderItem.taxedAmount) : orderItem.taxedAmount != null)
            return false;
        return tax != null ? tax.equals(orderItem.tax) : orderItem.tax == null;
    }


    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;

        final BigDecimal unitaryTax = product.getPrice().divide(valueOf(100)).multiply(product.getCategory().getTaxPercentage()).setScale(2, HALF_UP);
        final BigDecimal unitaryTaxedAmount = product.getPrice().add(unitaryTax).setScale(2, HALF_UP);

        this.taxedAmount = unitaryTaxedAmount.multiply(BigDecimal.valueOf(quantity)).setScale(2, HALF_UP);
        this.tax =  unitaryTax.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal getTaxedAmount() {
        return taxedAmount;
    }

    public BigDecimal getTax() {
        return tax;
    }

}
