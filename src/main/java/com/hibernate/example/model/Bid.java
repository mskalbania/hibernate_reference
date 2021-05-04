package com.hibernate.example.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Bid {

    @Id
    @GeneratedValue(generator = "uuid")
    private String id;

    @CreationTimestamp
    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdOn;

    @NotNull
    @Positive
    private BigDecimal amount;

//    @OneToOne
//    private Item item;
}
