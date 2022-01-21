package com.kamelchukov.autocatalog.model.dto.personDto.request;

import lombok.Data;

@Data
public class PersonCreateRequest {
    private String firstName;
    private String lastName;
}
