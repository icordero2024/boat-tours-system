package tours.service.controller.dto;

/**
 * DTO for incoming Tour creation requests.
 * 
 * We use a DTO instead of the entity to avoid exposing internal models directly.
 */
public class TourRequest {

    private String name;
    private String location;
    private double price;

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}