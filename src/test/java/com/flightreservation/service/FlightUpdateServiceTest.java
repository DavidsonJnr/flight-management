package com.flightreservation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flightreservation.exception.FlightConflictException;
import com.flightreservation.exception.FlightException;
import com.flightreservation.model.entity.Flight;
import com.flightreservation.model.request.FlightEditRequest;
import com.flightreservation.model.response.FlightResponse;
import com.flightreservation.model.response.ResponseMessage;
import com.flightreservation.repository.flight.FlightRepository;

@ExtendWith(MockitoExtension.class)
class FlightUpdateServiceTest {

	@Mock
	private FlightRepository flightRepository;

	@InjectMocks
	private FlightUpdateService flightUpdateService;

	@Test
	void testUpdateValidFlight() {
		FlightEditRequest request = FlightEditRequest.builder().airline("Iberia")
				.departureTime(OffsetDateTime.now().plusHours(1)).arrivalTime(OffsetDateTime.now().plusHours(2))
				.version(1L).build();

		Flight flight = new Flight();
		flight.setVersion(1L);

		when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
		when(flightRepository.saveAndFlush(any())).thenReturn(flight);

		ResponseEntity<ResponseMessage<FlightResponse>> response = flightUpdateService.update(1L, request);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Iberia", response.getBody().getResponseData().getAirline());
		verify(flightRepository, times(1)).saveAndFlush(flight);
	}

	@Test
	void testUpdateFlightVersionConflict() {
		FlightEditRequest request = FlightEditRequest.builder().version(0L).build();
		Flight flight = new Flight();
		flight.setVersion(1L);

		when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

		FlightConflictException exception = assertThrows(FlightConflictException.class,
				() -> flightUpdateService.update(1L, request));

		assertEquals("Entity version is wrong", exception.getMessage());
	}

	@Test
	void testUpdateFlightInvalidTimes() {
		FlightEditRequest request = FlightEditRequest.builder().departureTime(OffsetDateTime.now().plusHours(2))
				.arrivalTime(OffsetDateTime.now().plusHours(1)).version(1L).build();

		Flight flight = new Flight();
		flight.setVersion(1L);

		when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

		FlightException exception = assertThrows(FlightException.class, () -> flightUpdateService.update(1L, request));

		assertEquals("arrival.before.departure", exception.getMessageKey());
	}

}
