import controller.ClientsController;
import controller.OrdersController;
import controller.ProductsController;
import model.Clients;
import model.OrderItem;
import model.Orders;
import model.Products;
import repository.IRepository;
import repository.MemoryRepository;
import services.INotificador;
import services.NotificadorSimples;
import services.OrdersService;

import java.util.*;
import java.time.LocalDateTime;


public class Main {

    private static final Scanner SC = new Scanner(System.in);

    // In-memory repositories
    private static final IRepository<Clients, String> clientsRepo =
            new MemoryRepository<>(Clients::getId);
    private static final IRepository<Products, String> productsRepo =
            new MemoryRepository<>(Products::getId);
    private static final IRepository<Orders, String> ordersRepo =
            new MemoryRepository<>(Orders::getId);

    private static final INotificador notifier = new NotificadorSimples();

    // Controllers/Services
    private static final ClientsController clientsCtrl = new ClientsController(clientsRepo);
    private static final ProductsController productsCtrl = new ProductsController(productsRepo);
    private static final OrdersService ordersService = new OrdersService(ordersRepo, clientsRepo, notifier);
    private static final OrdersController ordersCtrl = new OrdersController(ordersService);

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            showMenu();
            int option = readInt("Select an option: ");
            switch (option) {
                case 1:
                    registerClient();
                    break;
                case 2:
                    registerProduct();
                    break;
                case 3:
                    registerOrder();
                    break;
                case 4:
                    consultClient();
                    break;
                case 5:
                    consultProduct();
                    break;
                case 6:
                    consultOrder();
                    break;
                case 7:
                    listAllClients();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
            System.out.println();
        }
    }

    private static void showMenu() {
        System.out.println("=== E-Commerce (REGISTRATION/QUERY) ===");
        System.out.println("1 - Register Client");
        System.out.println("2 - Register Product");
        System.out.println("3 - Register Order");
        System.out.println("4 - Query Client");
        System.out.println("5 - Query Product");
        System.out.println("6 - Query Order");
        System.out.println("7 - List All Clients");
        System.out.println("0 - Exit");
        System.out.println("====================================");
    }

    // 1) Register Client
    private static void registerClient() {
        System.out.println("Client Registration");
        System.out.println("Name: ");
        String name = SC.nextLine().trim();
        System.out.println("Document (CPF): ");
        String document = SC.nextLine().trim();
        System.out.println("Adress: ");
        String adress = SC.nextLine().trim();
        System.out.println("Phone Number: ");
        String phone = SC.nextLine().trim();
        System.out.println("Email: ");
        String email = SC.nextLine().trim();
        System.out.println("Birth Date: ");
        String birthDate = SC.nextLine().trim();

        String id = UUID.randomUUID().toString();
        clientsCtrl.register(id, document, name, phone, email, phone);
        System.out.println("Client registered with ID: " + id);
    }

    // 2) Register Product
    private static void registerProduct() {
        System.out.println("Product Registration");
        System.out.print("Name: ");
        String name = SC.nextLine().trim();
        System.out.print("Description: ");
        String description = SC.nextLine().trim();
        System.out.print("Sale Price: ");
        double salePrice = readDouble("Enter sale price: ");

        String id = UUID.randomUUID().toString();
        productsCtrl.register(id, name, description, salePrice);
        System.out.println("Product registered with ID: " + id);
    }

    // 3) Register Order
    private static void registerOrder() {
        System.out.println("Order Registration");

        System.out.println("Registered clients:");
        clientsCtrl.list();
        String clientId = readLine("Enter the Client ID for the order: ").trim();

        Clients c = clientsRepo.findById(clientId);
        if (c == null) {
            System.out.println("Client not found. Canceling.");
            return;
        }

        String orderId = UUID.randomUUID().toString();
        Orders order = ordersService.createOrders(orderId, clientId);
        System.out.println("Order created with ID: " + orderId + " for Client: " + clientId);


        boolean addMore = true;
        while (addMore) {
            System.out.print("Add item to the order? (y/n): ");
            String resp = SC.nextLine().trim().toLowerCase();
            if (!resp.equals("y")) {
                addMore = false;
                continue;
            }

            System.out.println("Available products:");
            productsCtrl.list();
            String prodId = readLine("Enter Product ID: ").trim();
            Products product = productsRepo.findById(prodId);
            if (product == null) {
                System.out.println("Product not found. Try again.");
                continue;
            }
            int qty = readInt("Quantity: ");
            String itemId = UUID.randomUUID().toString();

            OrderItem item = new OrderItem(itemId, product, qty, product.getPrice());
            ordersControllerAddItem(orderId, itemId, product, qty);
        }


        ordersCtrl.startPayment(orderId);
        System.out.println("Order " + orderId + " registered and pending payment.");
    }


    private static void ordersControllerAddItem(String orderId, String itemId, Products product, int qty) {
        OrderItem item = new OrderItem(itemId, product, qty, product.getPrice());
        ordersCtrl.addItem(orderId, itemId, product, qty);
    }

    // 4) Consult Client
    private static void consultClient() {
        System.out.println("Client Query");
        String id = readLine("Enter Client ID: ").trim();
        Clients c = clientsRepo.findById(id);
        if (c == null) {
            System.out.println("Client not found.");
        } else {
            System.out.println("ID: " + c.getId());
            System.out.println("Name: " + c.getName());
            System.out.println("Document: " + c.getCpf());
            System.out.println("Adress: " + c.getAdress());
            System.out.println("Telephone: " + c.getTelephone());
        }
    }

    // 5) Consult Product
    private static void consultProduct() {
        System.out.println("Product Query");
        String id = readLine("Enter Product ID: ").trim();
        Products p = productsRepo.findById(id);
        if (p == null) {
            System.out.println("Product not found.");
        } else {
            System.out.println("ID: " + p.getId());
            System.out.println("Name: " + p.getName());
            System.out.println("Description: " + p.getDescription());
            System.out.println("Sale Price: " + p.getPrice());
        }
    }

    // 6) Consult Order
    private static void consultOrder() {
        System.out.println("Order Query");
        String id = readLine("Enter Order ID: ").trim();
        Orders o = ordersRepo.findById(id);
        if (o == null) {
            System.out.println("Order not found.");
        } else {
            System.out.println("ID: " + o.getId());
            System.out.println("Client ID: " + o.getClientId());
            System.out.println("Creation Date: " + o.getCreateDate());
            System.out.println("Status: " + o.getStatus());
            System.out.println("Items:");
            for (OrderItem it : o.getItems()) {
                System.out.println(" - " + it.getId() + ": " + it.getProduct()
                        + " x " + it.getQuantity() + " @ " + it.getPrice()
                        + " -> Subtotal: " + it.getSubtotal());
            }
            System.out.println("Total: " + o.totalValue());
        }
    }


    private static String readLine(String prompt) {
        System.out.print(prompt);
        return SC.nextLine();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(SC.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(SC.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void listAllClients(){
        System.out.println("Clients: ");
        clientsCtrl.list();
    }
}