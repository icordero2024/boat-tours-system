package com.community.guides.model;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class CreateGuideRequest {
    private String name;
    private String community;
    private String boatName;

    public CreateGuideRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getBoatName() {
        return boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }
}