package com.community.tours.model;

import io.micronaut.core.annotation.Introspected;
import java.math.BigDecimal;

@Introspected
public class CreateTourRequest {
    private String name;
    private String location;
    private BigDecimal price;

    public CreateTourRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}