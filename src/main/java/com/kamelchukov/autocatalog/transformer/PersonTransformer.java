package com.kamelchukov.autocatalog.transformer;

import com.kamelchukov.autocatalog.model.Person;
import com.kamelchukov.autocatalog.model.dto.personDto.request.PersonCreateRequest;
import com.kamelchukov.autocatalog.model.dto.personDto.response.PersonResponse;

public final class PersonTransformer {

    private PersonTransformer() {
    }

    public static Person fromDto(PersonCreateRequest request) {
        return Person.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }

    public static PersonResponse toResponse(Person person) {
        return PersonResponse.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .cars(person.getCars())
                .build();
    }
}
