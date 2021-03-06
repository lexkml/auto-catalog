package com.kamelchukov.autocatalog.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("car")
@Data
@Builder
public class Car {
    @Id
    private Long id;

    private String model;
    private String year;
    private String color;
    private Long personId;
}


