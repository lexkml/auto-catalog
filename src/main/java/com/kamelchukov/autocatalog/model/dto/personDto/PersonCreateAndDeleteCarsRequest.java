package com.kamelchukov.autocatalog.model.dto.personDto;

import lombok.Data;

import java.util.Set;

@Data
public class PersonCreateAndDeleteCarsRequest {
    Set<Long> toAddCars;
    Set<Long> toDeleteCars;
}
