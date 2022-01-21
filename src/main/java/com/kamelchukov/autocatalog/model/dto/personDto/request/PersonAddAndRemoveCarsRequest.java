package com.kamelchukov.autocatalog.model.dto.personDto.request;

import lombok.Data;

import java.util.Set;

@Data
public class PersonAddAndRemoveCarsRequest {
    Set<Long> carsToRemove;
    Set<Long> carsToAdd;
}
