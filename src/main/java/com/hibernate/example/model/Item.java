package com.hibernate.example.model;

import com.hibernate.example.model.converter.MonetaryAmountTypeConverter;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Item {

    @Id
    @GeneratedValue(generator = "uuid")
    private String id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    private String description;

    @NotNull
    @Positive
    private BigDecimal initialPrice;

    @Formula("substr(description, 1, 10) || '...'")
    private String shortDescription;

//    @Formula("select avg(b.AMOUNT) from BID b where b.ITEM_ID = ID")
//    private BigDecimal averageBidAmount;

    @Convert(converter = MonetaryAmountTypeConverter.class) //To demonstrate type converters
    private MonetaryAmount buyNowPrice;

    protected Item() {
    }

    public Item(String name, String description, BigDecimal initialPrice) {
        this.name = name;
        this.description = description;
        this.initialPrice = initialPrice;
    }
}
