package com.kamelchukov.autocatalog.model.dto.personDto;

import com.kamelchukov.autocatalog.model.Person;
import lombok.Data;

@Data
public class PersonCreateRequest {
    private String firstName;
    private String lastName;

    public Person dtoToPerson() {
        return Person.builder()
                .firstName(getFirstName())
                .lastName(getLastName())
                .build();
    }
}
