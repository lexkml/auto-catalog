package com.kamelchukov.autocatalog.model.dto.carDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
public class CarUpdateRequest {
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private String model;

    @JsonIgnore
    private char classCar;

    @JsonIgnore
    private String year;

    @JsonIgnore
    private String color;

    private int price;

    @JsonIgnore
    private Long personId = null;
}

