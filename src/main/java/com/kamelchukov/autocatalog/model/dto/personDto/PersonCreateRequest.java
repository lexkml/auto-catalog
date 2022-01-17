package com.kamelchukov.autocatalog.model.dto.personDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kamelchukov.autocatalog.model.Person;
import lombok.*;

@Data
public class PersonCreateRequest {
    @JsonIgnore
    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    public Person dtoToPerson() {
        return Person.builder()
                .id(getId())
                .firstName(getFirstName())
                .lastName(getLastName())
                .build();
    }
}
