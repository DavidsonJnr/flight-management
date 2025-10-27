package com.flightreservation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flightreservation.exception.FlightConflictException;
import com.flightreservation.exception.FlightException;
import com.flightreservation.exception.FlightNotFoundException;
import com.flightreservation.model.entity.Flight;
import com.flightreservation.model.enums.AirportEnum;
import com.flightreservation.model.request.FlightEditRequest;
import com.flightreservation.model.response.FlightResponse;
import com.flightreservation.model.response.ResponseMessage;
import com.flightreservation.repository.flight.FlightRepository;

class FlightUpdateServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightUpdateService flightUpdateService;

    private Flight flight;
    private FlightEditRequest validRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        flight = new Flight();
        flight.setId(1L);
        flight.setAirline("TAP");
        flight.setSupplier("Test");
        flight.setFare(new BigDecimal(100));
        flight.setDepartureAirport(AirportEnum.BCN);
		flight.setDestinationAirport(AirportEnum.MAD);
        flight.setDepartureTime(OffsetDateTime.now().plusHours(1));
        flight.setArrivalTime(OffsetDateTime.now().plusHours(3));
        flight.setVersion(1L);
        flight.setCreatedAt(Instant.now());
        flight.setCreatedBy("test-user");

        validRequest = FlightEditRequest.builder()
        		.airline("TAP")
        		.supplier("Test")
        		.departureAirport(AirportEnum.BCN)
        		.destinationAirport(AirportEnum.MAD)
        		.fare(new BigDecimal(100))
                .departureTime(OffsetDateTime.now().plusHours(2))
                .arrivalTime(OffsetDateTime.now().plusHours(4))
                .version(1L)
                .build();
    }

    @Test
    void testUpdateFlightSuccess() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightRepository.saveAndFlush(any(Flight.class))).thenReturn(flight);

        ResponseEntity<ResponseMessage<FlightResponse>> response = flightUpdateService.update(1L, validRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(flight.getId(), response.getBody().getResponseData().getId());
        verify(flightRepository).saveAndFlush(any(Flight.class));
    }

    @Test
    void testUpdateFlightNotFound() {
        when(flightRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(FlightNotFoundException.class, () -> flightUpdateService.update(1L, validRequest));

        verify(flightRepository, never()).save(any());
    }

    @Test
    void testUpdateFlightVersionConflict() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        FlightEditRequest request = FlightEditRequest.builder()
                .departureTime(OffsetDateTime.now().plusHours(2))
                .arrivalTime(OffsetDateTime.now().plusHours(3))
                .version(99L)
                .build();

        FlightConflictException exception = assertThrows(FlightConflictException.class,
                () -> flightUpdateService.update(1L, request));

        assertEquals("flight.version.conflict", exception.getMessageKey());
    }

    @Test
    void testUpdateFlightInvalidTimes() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        FlightEditRequest request = FlightEditRequest.builder()
                .departureTime(OffsetDateTime.now().plusHours(2))
                .arrivalTime(OffsetDateTime.now().plusHours(1))
                .version(1L)
                .build();

        FlightException exception = assertThrows(FlightException.class,
                () -> flightUpdateService.update(1L, request));

        assertEquals("arrival.before.departure", exception.getMessageKey());
        verify(flightRepository, never()).save(any());
    }
}
