package com.kamelchukov.autocatalog.model.dto.personDto;

import lombok.Data;

@Data
public class PersonUpdateRequest {
    private String firstName;
    private String lastName;
}
