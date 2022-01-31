package com.kamelchukov.autocatalog.model.dto.personDto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PersonCreateRequest {
    private String firstName;
    private String lastName;
}
