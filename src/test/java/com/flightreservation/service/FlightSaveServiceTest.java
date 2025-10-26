package com.flightreservation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flightreservation.exception.FlightException;
import com.flightreservation.model.entity.Flight;
import com.flightreservation.model.request.FlightRequest;
import com.flightreservation.model.response.FlightResponse;
import com.flightreservation.model.response.ResponseMessage;
import com.flightreservation.repository.flight.FlightRepository;

@ExtendWith(MockitoExtension.class)
class FlightSaveServiceTest {

	@Mock
	private FlightRepository flightRepository;

	@InjectMocks
	private FlightSaveService flightSaveService;

	@Test
	void testSaveValidFlight() {
		FlightRequest request = FlightRequest.builder()
                .airline("TAP")
                .departureTime(OffsetDateTime.now().plusHours(1))
                .arrivalTime(OffsetDateTime.now().plusHours(2))
                .build();

        Flight savedFlight = new Flight();
        BeanUtils.copyProperties(request, savedFlight);

        when(flightRepository.save(any(Flight.class))).thenReturn(savedFlight);

        ResponseEntity<ResponseMessage<FlightResponse>> response = flightSaveService.save(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("TAP", response.getBody().getResponseData().getAirline());
        verify(flightRepository, times(1)).save(any(Flight.class));
	}
	
	@Test
    void testSaveInvalidFlightTimes() {
        FlightRequest request = FlightRequest.builder()
                .departureTime(OffsetDateTime.now().plusHours(2))
                .arrivalTime(OffsetDateTime.now().plusHours(1))
                .build();

        FlightException exception = assertThrows(FlightException.class, () -> flightSaveService.save(request));
        assertEquals("arrival.before.departure", exception.getMessageKey());
    }

}
