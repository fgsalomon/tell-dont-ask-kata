package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.useCase.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static it.gabrieletondi.telldontaskkata.domain.OrderStatus.CREATED;
import static it.gabrieletondi.telldontaskkata.domain.OrderStatus.REJECTED;
import static it.gabrieletondi.telldontaskkata.domain.OrderStatus.SHIPPED;

public class Order {
    private BigDecimal total;
    private String currency;
    private List<OrderItem> items;
    private BigDecimal tax;
    private OrderStatus status;
    private int id;

    public Order(int id){
        this();
        this.id = id;
    }

    public Order(int id, OrderStatus status){
        this();
        this.id = id;
        this.status = status;
    }

    public Order(List<OrderItem> items){
        this();
        this.items = items;
        updateTotalAndTax();
    }

    public Order() {
        this.currency = "EUR";
        this.tax = BigDecimal.ZERO;
        this.status = OrderStatus.CREATED;
        this.items = new ArrayList<>();
        this.total = BigDecimal.ZERO;
        this.id = 0;
    }

    private void updateTotalAndTax(){
        total = BigDecimal.ZERO;
        tax = BigDecimal.ZERO;
        for (OrderItem addedItem: items) {
            total = total.add(addedItem.getTaxedAmount());
            tax = tax.add(addedItem.getTax());
        }
    }

    public void addItem(OrderItem item){
        items.add(item);
        updateTotalAndTax();
    }

    public OrderStatus getStatus() {
        return status;
    }


    public int getId() {
        return id;
    }

    public void ship(){

        if (status.equals(CREATED) || status.equals(REJECTED)) {
            throw new OrderCannotBeShippedException();
        }

        if (status.equals(SHIPPED)) {
            throw new OrderCannotBeShippedTwiceException();
        }

        this.status = OrderStatus.SHIPPED;
    }

    public void approve(){
        checkIfShipped();
        if (status.equals(OrderStatus.REJECTED)) {
            throw new RejectedOrderCannotBeApprovedException();
        }
        status = OrderStatus.APPROVED;
    }

    public void reject(){
        checkIfShipped();
        if (status.equals(OrderStatus.APPROVED)) {
            throw new ApprovedOrderCannotBeRejectedException();
        }
        status = OrderStatus.REJECTED;
    }

    private void checkIfShipped(){
        if (status.equals(OrderStatus.SHIPPED)) {
            throw new ShippedOrdersCannotBeChangedException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (total != null ? !total.equals(order.total) : order.total != null) return false;
        if (currency != null ? !currency.equals(order.currency) : order.currency != null) return false;
        if (items != null ? !items.equals(order.items) : order.items != null) return false;
        if (tax != null ? !tax.equals(order.tax) : order.tax != null) return false;
        return status == order.status;
    }
}
