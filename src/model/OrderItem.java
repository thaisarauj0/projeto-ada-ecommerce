package model;


public class OrderItem {
    private String id;
    private Products product;
    private int quantity;
    private double price;

    public OrderItem(String id, Products product, int quantity, double price) {
        if(quantity<0)throw new IllegalArgumentException("Quantity cannot be negative");
        if(price<0)throw new IllegalArgumentException("Price cannot be negative");
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice();
    }

    public String getId() { return this.id; }
    public Products getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public void setQuantity(int quantity) {
        if(quantity<0)throw new IllegalArgumentException("Quantity cannot be negative");
        this.quantity = quantity;
    }
    public double getSubtotal(){
        return price*quantity;
    }
}
