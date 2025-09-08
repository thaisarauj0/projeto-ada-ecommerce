package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Orders {
    private String id;
    private String clientId;
    private LocalDateTime createDate;
    private List<OrderItem> itens = new ArrayList<>();
    private OrderStatus status;
    private PaymentStatus paymentStatus;

    // Construtor completo (esse deve ser usado sempre!)
    public Orders(String id, String clientId){
        this.id = id;
        this.clientId = clientId;
        this.createDate = LocalDateTime.now();
        this.status = OrderStatus.OPEN;
        this.paymentStatus = PaymentStatus.WAITING_PAYMENT;
    }

    public Orders() {
        this.createDate = LocalDateTime.now();
        this.status = OrderStatus.OPEN;
        this.paymentStatus = PaymentStatus.WAITING_PAYMENT;
    }

    public void addItemToOrders(OrderItem item) {
        if(status != OrderStatus.OPEN){
            throw new IllegalArgumentException("It's not possible add item when the status is not 'OPEN'");
        }
        itens.add(item);
    }

    public void removeItemFromOrders(String itemId) {
        if(status != OrderStatus.OPEN){
            throw new IllegalArgumentException("It's not possible remove item when the status is not 'OPEN'");
        }
        itens.removeIf(it -> it.getId().equals(itemId));
    }

    public void updateQuantity(String itemId, int quantity) {
        if(status != OrderStatus.OPEN){
            throw new IllegalArgumentException("It's not possible update quantity when the status is not 'OPEN'");
        }
        for(OrderItem item: itens){
            if(item.getId().equals(itemId)){
                item.setQuantity(quantity);
                return;
            }
        }
        throw new IllegalArgumentException("Item not found");
    }

    public double totalValue() {
        return itens.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public void paymentStart(){
        if(itens.isEmpty() || totalValue() <= 0 ){
            throw new IllegalArgumentException("It's not possible payment start when the total value is 0");
        }
        this.status = OrderStatus.WAITING_PAYMENT;
        this.paymentStatus = PaymentStatus.WAITING_PAYMENT;
    }

    public void paymentConfirm(){
        this.paymentStatus = PaymentStatus.PAID;
        this.status = OrderStatus.PAID;
    }

    public void deliveryStart(){
        if(this.status != OrderStatus.PAID){
            throw new IllegalArgumentException("It's not possible delivery start when the status is not 'PAID'");
        }
        this.status = OrderStatus.DELIVERED;
    }

    public void finishOrder(){
        if(this.status != OrderStatus.DELIVERED){
            throw new IllegalArgumentException("It's not possible finish order when the status is not 'DELIVERED'");
        }
        this.status = OrderStatus.FINISHED;
    }

    // Getters
    public String getId() { return id; }
    public String getClientId(){ return this.clientId; }
    public LocalDateTime getCreateDate(){ return this.createDate; }
    public List<OrderItem> getItems(){ return this.itens; }
    public OrderStatus getStatus(){ return this.status; }
    public PaymentStatus getPaymentStatus(){ return this.paymentStatus; }
}
