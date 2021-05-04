package com.hibernate.example.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@Data
public class City {

    @NotNull
    private String zipCode;

    @NotNull
    private String name;

    @NotNull
    private String country;

    protected City() {
    }

    public City(String zipCode, String name, String country) {
        this.zipCode = zipCode;
        this.name = name;
        this.country = country;
    }
}
