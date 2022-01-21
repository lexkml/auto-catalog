package com.kamelchukov.autocatalog.model.dto.carDto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarResponse {
    private Long id;
    private String model;
    private char classCar;
    private String year;
    private String color;
    private Long personId;
}
