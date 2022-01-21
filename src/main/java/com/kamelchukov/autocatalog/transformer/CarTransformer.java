package com.kamelchukov.autocatalog.transformer;

import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.dto.carDto.request.CarCreateRequest;
import com.kamelchukov.autocatalog.model.dto.carDto.response.CarResponse;

public final class CarTransformer {
    private CarTransformer() {
    }

    public static Car fromDto(CarCreateRequest request) {
        return Car.builder()
                .model(request.getModel())
                .classCar(request.getClassCar())
                .year(request.getYear())
                .color(request.getColor())
                .personId(request.getPersonId())
                .build();
    }

    public static CarResponse toResponse(Car car) {
        return CarResponse.builder()
                .id(car.getId())
                .model(car.getModel())
                .classCar(car.getClassCar())
                .year(car.getYear())
                .color(car.getColor())
                .personId(car.getPersonId())
                .build();
    }
}
