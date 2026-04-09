package tours.service.controller.dto;

import io.micronaut.serde.annotation.Serdeable;

/**
 * DTO for incoming Tour creation requests.
 */
@Serdeable
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