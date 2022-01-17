package com.kamelchukov.autocatalog.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("car")
@Data
@Builder
public class Car {
    @Id
    private Long id;

    private String model;

    @Column("class")
    private char classCar;

    private String year;
    private String color;
    private int price;

    @Column("person_id")
    private Long personId = null;
}


