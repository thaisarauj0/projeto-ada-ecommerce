package model;

import java.util.UUID;

public class Products {
    private String id;
    private String name;
    private Double price;
    private String description;


    public Products(String id, String name, String description, Double price) {
        if(price<0)throw new IllegalArgumentException("Price cannot be negative");
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

}
