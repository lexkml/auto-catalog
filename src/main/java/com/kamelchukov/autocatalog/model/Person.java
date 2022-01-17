package com.kamelchukov.autocatalog.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table
@Data
@Builder
public class Person {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    @MappedCollection(idColumn = "person_id")
    private Set<Car> cars;
}
