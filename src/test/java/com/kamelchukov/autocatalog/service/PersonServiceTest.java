package com.kamelchukov.autocatalog.service;

import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.Person;
import com.kamelchukov.autocatalog.model.dto.personDto.request.PersonAddAndRemoveCarsRequest;
import com.kamelchukov.autocatalog.model.dto.personDto.request.PersonCreateRequest;
import com.kamelchukov.autocatalog.repository.PersonRepository;
import com.kamelchukov.autocatalog.transformer.PersonTransformer;
import com.kamelchukov.common.exception.EntityNotFoundException;
import com.kamelchukov.common.exception.IncorrectDataException;
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

    private static final Car CAR = Car.builder()
            .id(2L)
            .model("Tesla")
            .color("Black")
            .year("2020")
            .personId(null)
            .build();

    private static final Person PERSON = Person.builder()
            .id(5L)
            .firstName("Ivan")
            .lastName("Ivanov")
            .cars(Collections.emptySet())
            .build();

    @BeforeEach
    void beforeEach() {
        lenient().when(personRepository.findById(anyLong())).thenReturn(Optional.of(PERSON));
    }

    @Test
    void createTest_successfulCase() {
        var request = PersonCreateRequest.builder()
                .firstName(PERSON.getFirstName())
                .lastName(PERSON.getLastName())
                .build();

        var actual = PersonTransformer.fromDto(request);
        actual.setId(PERSON.getId());
        actual.setCars(PERSON.getCars());

        when(personRepository.save(any(Person.class))).thenReturn(PERSON);

        personService.create(request);

        assertEquals(PERSON, actual);
    }

    @Test
    void findByIdTest_successfulCase() {
        var actual = personService.findById(anyLong());

        assertEquals(PERSON, actual);
    }

    @Test
    void findByIdTest_IfPersonNotFound() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> personService.findById(anyLong()));
    }

    @Test
    void findAllTest_successfulCase() {
        var persons = List.of(PERSON);
        when(personRepository.findAll()).thenReturn(persons);

        var actual = personService.findAll();

        assertThat(persons).containsAll(actual);
    }

    @Test
    void deleteTest_successfulCase() {
        when(personRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(personRepository).deleteById(anyLong());

        personService.delete(anyLong());

        verify(personRepository).deleteById(anyLong());
    }

    @Test
    void deleteTest_IfPersonNotFound() {
        when(personRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> personService.delete(anyLong()));
    }

    @Test
    void addCarsToPersonTest_successfulCase() {
        var carToAdd = CAR;
        carToAdd.setPersonId(null);

        when(carService.findById(anyLong())).thenReturn(carToAdd);
        doNothing().when(carService).save(carToAdd);

        personService.addCarsToPerson(PERSON.getId(), Set.of(anyLong()));

        verify(carService).save(any(Car.class));
    }

    @Test
    void addCarsToPersonTest_IfCarOwnedOtherPerson() {
        var carToAdd = CAR;
        carToAdd.setPersonId(10L);
        when(carService.findById(anyLong())).thenReturn(carToAdd);

        assertThrows(IncorrectDataException.class,
                () -> personService.addCarsToPerson(PERSON.getId(), Set.of(anyLong())));
    }

    @Test
    void removeCarsFromPersonTest_successful() {
        var carToRemove = CAR;
        carToRemove.setPersonId(PERSON.getId());

        when(carService.findById(anyLong())).thenReturn(carToRemove);
        doNothing().when(carService).save(carToRemove);
        PersonTransformer.toResponse(PERSON);

        personService.removeCarsFromPerson(PERSON.getId(), Set.of(anyLong()));

        verify(carService).save(any(Car.class));
    }

    @Test
    void removeCarsFromPersonTest_ifCarNotOwnedThisPerson() {
        var carToRemove = CAR;
        carToRemove.setPersonId(8L);

        when(carService.findById(anyLong())).thenReturn(carToRemove);

        assertThrows(IncorrectDataException.class,
                () -> personService.removeCarsFromPerson(PERSON.getId(), Set.of(anyLong())));
    }

    @Test
    void removeCarsFromPersonTest_ifPersonIdByCarIsNull() {
        var carToRemove = CAR;
        carToRemove.setPersonId(null);

        when(carService.findById(anyLong())).thenReturn(carToRemove);

        assertThrows(IncorrectDataException.class,
                () -> personService.removeCarsFromPerson(PERSON.getId(), Set.of(anyLong())));
    }

    @Test
    void addAndRemoveCarsFromPersonTest_successfulCase() {
        PersonService personServiceSpy = spy(new PersonService(personRepository, carService));
        PersonAddAndRemoveCarsRequest request = new PersonAddAndRemoveCarsRequest();
        request.setCarsToRemove(Set.of(1L));
        request.setCarsToAdd(Set.of(2L));

        doReturn(null).when(personServiceSpy).removeCarsFromPerson(anyLong(), any());
        doReturn(null).when(personServiceSpy).addCarsToPerson(anyLong(), any());

        personServiceSpy.addAndRemoveCarsFromPerson(PERSON.getId(), request);

        verify(personServiceSpy).removeCarsFromPerson(PERSON.getId(), request.getCarsToRemove());
        verify(personServiceSpy).addCarsToPerson(PERSON.getId(), request.getCarsToAdd());
    }
}