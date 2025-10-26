package com.flightreservation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flightreservation.repository.flight.FlightRepository;

@ExtendWith(MockitoExtension.class)
class FlightDeleteServiceTest {

	@Mock
	private FlightRepository flightRepository;

	@InjectMocks
	private FlightDeleteService flightDeleteService;

	@Test
	void testDeleteFutureFlight() {
		when(flightRepository.existsFutureFlightById(anyLong(), any())).thenReturn(true);

		ResponseEntity<Void> response = flightDeleteService.delete(1L);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(flightRepository, times(1)).deleteById(1L);
	}

	@Test
	void testDeleteNonExistingFlight() {
		when(flightRepository.existsFutureFlightById(anyLong(), any())).thenReturn(false);

		ResponseEntity<Void> response = flightDeleteService.delete(1L);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

}
