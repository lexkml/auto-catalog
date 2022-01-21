package com.kamelchukov.autocatalog.model.dto.carDto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarCreateRequest {
    private String model;
    private char classCar;
    private String year;
    private String color;
    private Long personId;
}

