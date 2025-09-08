package services;

import model.Clients;
import model.OrderItem;
import model.Orders;
import repository.IRepository;

public class OrdersService {
    private IRepository<Orders, String> ordersRepo;
    private IRepository<Clients, String> clientsRepo;
    private INotificador notificador;

    public OrdersService(IRepository<Orders, String> ordersRepo, IRepository<Clients, String> clientsRepo, INotificador notificador) {
        this.ordersRepo = ordersRepo;
        this.clientsRepo = clientsRepo;
        this.notificador = notificador;
    }

    public Orders createOrders(String idOrders, String idClient ){
        if (clientsRepo.findById(idClient) == null) {
            throw new IllegalArgumentException("Client not found");
        }
        Orders orders = new Orders(idOrders, idClient);
        ordersRepo.add(orders);
        return orders;
    }

    public Orders addItemToOrders(String idOrders, OrderItem item){
        Orders orders = ordersRepo.findById(idOrders);
        if (orders == null) {
            throw new IllegalArgumentException("Order not found: " + idOrders);
        }
        orders.addItemToOrders(item);
        ordersRepo.update(orders);
        return orders;
    }


    public void deleteItemFromOrders(String idOrders, String idClient, String idItem){
        Orders orders = ordersRepo.findById(idOrders);
        if(orders == null)throw new IllegalArgumentException("Order not found");
        orders.removeItemFromOrders(idItem);
        ordersRepo.update(orders);
    }

    public void updateItemQuantity(String idOrders, String idClient, String idItem, int quantity){
        Orders orders = ordersRepo.findById(idOrders);
        if(orders == null)throw new IllegalArgumentException("Order not found");
        orders.updateQuantity(idItem, quantity);
        ordersRepo.update(orders);
    }
    public void startPayment(String idOrders){
        Orders orders = ordersRepo.findById(idOrders);
        if(orders == null)throw new IllegalArgumentException("Order not found");
        orders.paymentStart();
        ordersRepo.update(orders);
        notificador.sendEmail(getClientEmail(orders.getClientId()), "Open Order - Waiting Payment", "Your Order " + idOrders + " is waiting for Payment");
    }

    public void paymentConfirm(String idOrders){
        Orders orders = ordersRepo.findById(idOrders);
        if(orders == null)throw new IllegalArgumentException("Order not found");
        orders.paymentConfirm();
        ordersRepo.update(orders);
        notificador.sendEmail(getClientEmail(orders.getClientId()), "Confirmed Payment", " The payment for order " + idOrders + " is confirmed");
    }

    public void startDelivery(String idOrders){
        Orders orders = ordersRepo.findById(idOrders);
        if(orders == null)throw new IllegalArgumentException("Order not found");
        orders.deliveryStart();
        ordersRepo.update(orders);
        notificador.sendEmail(getClientEmail(orders.getClientId()), "Delivered Order", "Your Order " + idOrders + " is delivered");
    }

    public void finishOrder(String idOrders){
        Orders orders = ordersRepo.findById(idOrders);
        if(orders == null)throw new IllegalArgumentException("Order not found");
        orders.finishOrder();
        ordersRepo.update(orders);
        notificador.sendEmail(getClientEmail(orders.getClientId()), "Finished Order", "Your Order " + idOrders + " is finished");
    }

    private String getClientEmail(String idClient){
        return idClient + "@email.com";
    }

    public double OrderAmount(String idOrders){
        Orders orders = ordersRepo.findById(idOrders);
        if(orders == null)throw new IllegalArgumentException("Order not found");
        return orders.totalValue();
    }
}
