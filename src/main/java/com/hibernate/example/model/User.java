package com.hibernate.example.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    private String id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @NotNull
    @Size(min = 4, max = 15)
    private String userName;

    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 20)
    private String lastName;

    @NotNull
    private String passwordHash;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Type type;

    @Valid //Required to validate embedded object
    private Address homeAddress;

    @Valid
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "billing_street")),
            @AttributeOverride(name = "city.name", column = @Column(name = "billing_city")),
            @AttributeOverride(name = "city.zipCode", column = @Column(name = "billing_zipCode")),
            @AttributeOverride(name = "city.country", column = @Column(name = "billing_country"))
    })
    private Address billingAddress;

    protected User() {
    }

    public User(String userName, String firstName, String lastName, String passwordHash, Type type) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.type = type;
    }

    enum Type {
        ADMIN, PREMIUM, CASUAL
    }
}
