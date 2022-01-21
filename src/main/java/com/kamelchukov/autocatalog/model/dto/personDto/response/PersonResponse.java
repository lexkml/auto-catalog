package com.kamelchukov.autocatalog.model.dto.personDto.response;

import com.kamelchukov.autocatalog.model.Car;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PersonResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<Car> cars;
}
