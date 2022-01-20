package com.kamelchukov.autocatalog.model.dto.carDto;

import com.kamelchukov.autocatalog.model.Car;
import lombok.Data;

@Data
public class CarCreateRequest {

    private String model;
    private char classCar;
    private String year;
    private String color;
    private int price;
    private Long personId;

    public Car dtoToCar() {
        return Car.builder()
                .model(getModel())
                .classCar(getClassCar())
                .year(getYear())
                .color(getColor())
                .price(getPrice())
                .personId(getPersonId())
                .build();
    }
}

