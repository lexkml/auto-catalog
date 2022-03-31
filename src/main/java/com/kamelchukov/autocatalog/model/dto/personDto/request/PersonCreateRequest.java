package com.kamelchukov.autocatalog.model.dto.personDto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonCreateRequest {
    private String firstName;
    private String lastName;
}
