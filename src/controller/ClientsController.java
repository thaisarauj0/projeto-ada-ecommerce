package controller;

import model.Clients;
import repository.IRepository;


public class ClientsController {
    private IRepository<Clients, String> clientRepo;

    public ClientsController(IRepository<Clients, String> repository) {
        this.clientRepo = repository;
    }

    public void register( String id, String cpf, String name, String telephone, String email, String address) {
        Clients client = new Clients( id, name, cpf, telephone, email, address);
        clientRepo.add(client);
        System.out.println("Client registered successfully");
    }

    public void list() {
        for (Clients c : clientRepo.listAll()) {
            System.out.println(c.getId()+ " | " + c.getName() + " | " + c.getCpf() + " | " + c.getTelephone() + " | " + c.getEmail() + "\n");
        }
    }

    public void update(String id, String cpf, String name, String telephone, String email, String address) {
        Clients client = new Clients(id, name, cpf, telephone, email, address);
        if (client == null) throw new IllegalArgumentException("Client not found!");
        Clients updated = new Clients(id, name, cpf, telephone, email, address);
        clientRepo.update(updated);
    }

}
