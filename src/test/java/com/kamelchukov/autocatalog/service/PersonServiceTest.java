package com.kamelchukov.autocatalog.service;

import com.kamelchukov.autocatalog.exception.EntityNotFoundException;
import com.kamelchukov.autocatalog.exception.IncorrectDataException;
import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.Person;
import com.kamelchukov.autocatalog.model.dto.personDto.request.PersonAddAndRemoveCarsRequest;
import com.kamelchukov.autocatalog.model.dto.personDto.request.PersonCreateRequest;
import com.kamelchukov.autocatalog.repository.PersonRepository;
import com.kamelchukov.autocatalog.transformer.PersonTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private CarService carService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private Car car;

    private static Person person;


    @BeforeEach
    void beforeEach() {
        person = Person.builder()
                .id(5L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .cars(Collections.emptySet())
                .build();
    }

    @Test
    void create() {
        var request = PersonCreateRequest.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .build();

        var createdPerson = PersonTransformer.fromDto(request);
        createdPerson.setId(person.getId());
        createdPerson.setCars(person.getCars());

        when(personRepository.save(any(Person.class))).thenReturn(person);

        personService.create(request);

        assertEquals(createdPerson, person);
    }

    @Test
    void findById() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));

        var result = personService.findById(anyLong());

        assertEquals(result, person);
    }

    @Test
    void findByIdIfPersonNotFound() {
        assertThrows(EntityNotFoundException.class, () -> personService.findById(anyLong()));
    }

    @Test
    void findAll() {
        var persons = List.of(person);
        when(personRepository.findAll()).thenReturn(persons);

        var result = personService.findAll();

        assertThat(result).containsAll(persons);
    }

    @Test
    void delete() {
        when(personRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(personRepository).deleteById(anyLong());

        personService.delete(anyLong());

        verify(personRepository).deleteById(anyLong());
    }

    @Test
    void deleteIfPersonNotFound() {
        assertThrows(EntityNotFoundException.class, () -> personService.delete(anyLong()));
    }

    @Test
    void addCarsToPerson() {
        when(carService.findById(anyLong())).thenReturn(car);
        when(car.getPersonId()).thenReturn(null);
        doNothing().when(car).setPersonId(anyLong());
        doNothing().when(carService).save(any(Car.class));
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));

        personService.addCarsToPerson(anyLong(), Set.of(1L));

        verify(carService, atLeast(1)).save(any(Car.class));
    }

    @Test
    void addCarsToPersonIfCarOwnedOtherPerson() {
        when(carService.findById(anyLong())).thenReturn(car);
        when(car.getPersonId()).thenReturn(2L);

        assertThrows(IncorrectDataException.class,
                () -> personService.addCarsToPerson(anyLong(), Set.of(2L)));
    }

    @Test
    void removeCarsFromPerson() {
        when(carService.findById(anyLong())).thenReturn(car);
        when(car.getPersonId()).thenReturn(5L);
        doNothing().when(car).setPersonId(null);
        doNothing().when(carService).save(car);

        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));

        personService.removeCarsFromPerson(5L, Set.of(1L));

        verify(carService, atLeast(1)).save(any(Car.class));
    }

    @Test
    void removeCarsFromPersonIfCarsNotOwnedHim() {
        when(carService.findById(anyLong())).thenReturn(car);
        when(car.getPersonId()).thenReturn(2L);

        assertThrows(IncorrectDataException.class,
                () -> personService.removeCarsFromPerson(5L, Set.of(anyLong())));
    }

    @Test
    void addAndRemoveCarsFromPerson() {
        PersonAddAndRemoveCarsRequest request = new PersonAddAndRemoveCarsRequest();
        request.setCarsToRemove(Set.of(1L));
        request.setCarsToAdd(Set.of(2L));
        Long personId = 5L;

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        when(carService.findById(1L)).thenReturn(car);
        when(car.getPersonId()).thenReturn(personId);
        doNothing().when(car).setPersonId(null);
        doNothing().when(carService).save(car);

        when(carService.findById(2L)).thenReturn(car);
        when(car.getPersonId()).thenReturn(null);
        doNothing().when(car).setPersonId(personId);
        doNothing().when(carService).save(car);

        personService.addAndRemoveCarsFromPerson(personId, request);

        verify(carService, atLeast(2)).save(any(Car.class));
    }
}