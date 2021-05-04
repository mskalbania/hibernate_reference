package com.hibernate.example.model;

import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Embeddable
@Data
public class Address {

    @NotNull
    private String street;

    @NotNull
    @Valid
    @AttributeOverrides(
            @AttributeOverride(name = "name", column = @Column(name = "city"))
    )
    private City city;

    protected Address() {
    }

    public Address(String street, City city) {
        this.street = street;
        this.city = city;
    }
}
