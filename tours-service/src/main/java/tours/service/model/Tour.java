package tours.service.model;

import jakarta.persistence.*;

@Entity // Marca esta clase como tabla en la BD
public class Tour {

    @Id
    @GeneratedValue // ID autogenerado
    private Long id;

    private String name;
    private String location;
    private double price;

    public Tour() {}

    // Getters y setters
    public Long getId() { return id; }

    public String getName() { return name; }

    public String getLocation() { return location; }

    public double getPrice() { return price; }

    public void setName(String name) { this.name = name; }

    public void setLocation(String location) { this.location = location; }

    public void setPrice(double price) { this.price = price; }
}