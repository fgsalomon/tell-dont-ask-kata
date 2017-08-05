package it.gabrieletondi.telldontaskkata.useCase;

public class SellItemRequest {
    private String productName;
    private int quantity;

    public SellItemRequest(String productName) {
        this.productName = productName;
        this.quantity = 1;
    }

    public SellItemRequest(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }
}
