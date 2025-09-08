package controller;

import model.Products;
import repository.IRepository;

public class ProductsController {
    private IRepository<Products, String> productRepo;

    public ProductsController(IRepository<Products, String> productRepo) {
        this.productRepo = productRepo;
    }

    public void register(String id, String name, String description, double price) {
        Products product = new Products(id, name, description, price);
        productRepo.add(product);
    }

    public void list(){
        for (Products product : productRepo.listAll()){
            System.out.println(product.getId()+" | "+product.getName()+" | "+product.getDescription()+" | "+product.getPrice());
        }
    }

    public void update(String id, String name, String description, double price) {
        Products product = new Products(id, name, description, price);
        productRepo.update(product);
    }
}
