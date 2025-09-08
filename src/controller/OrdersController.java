package controller;


import model.OrderItem;
import model.Orders;
import model.Products;
import services.OrdersService;

public class OrdersController {
    private OrdersService orderService;


    public OrdersController(OrdersService orderService) {
        this.orderService = orderService;
    }

    public Orders createOrders(String idOrder, String idClient ){
        return orderService.createOrders(idOrder, idClient);
    }

  public void addItem(String idOrder, String itemId, Products product, int quantity) {
        OrderItem item = new OrderItem(itemId, product, quantity, product.getPrice());
        orderService.addItemToOrders(idOrder, item);
  }

  public void startPayment(String idOrder) {

        double total = orderService.OrderAmount(idOrder);

        if (total <= 0) {
            System.out.println("Não é possível iniciar pagamento: o pedido não possui itens.");
            return;
        }

        orderService.startPayment(idOrder);}


    public void pay(String idOrder){
        orderService.paymentConfirm(idOrder);
    }

    public void delivery(String idOrder){
        orderService.startDelivery(idOrder);
    }

    public void finish(String idOrder){
        orderService.finishOrder(idOrder);
    }

    public void listOrders(){}
}
