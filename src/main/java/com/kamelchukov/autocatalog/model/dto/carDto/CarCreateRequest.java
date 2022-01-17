package com.kamelchukov.autocatalog.model.dto.carDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kamelchukov.autocatalog.model.Car;
import lombok.*;

@Data
public class CarCreateRequest {
    @JsonIgnore
    private Long id;

    private String model;

    @JsonProperty("class_car")
    private char classCar;

    private String year;
    private String color;
    private int price;

    @JsonProperty("person_id")
    private Long personId = null;

    public Car dtoToCar() {
        return Car.builder()
                .id(getId())
                .model(getModel())
                .classCar(getClassCar())
                .year(getYear())
                .color(getColor())
                .price(getPrice())
                .personId(getPersonId())
                .build();
    }
}

