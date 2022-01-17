package com.kamelchukov.autocatalog.model.dto.personDto;

import lombok.*;

import java.util.Set;

@Data
public class PersonAddOrDeleteCarsRequest {

    private Set<Long> cars;
}
