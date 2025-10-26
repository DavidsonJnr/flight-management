package com.flightreservation.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightreservation.repository.flight.FlightRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightDeleteService {
	
	private final FlightRepository flightRepository;
	
	@Transactional
	public ResponseEntity<Void> delete(Long id) {
		
		boolean exists = flightRepository.existsFutureFlightById(id, OffsetDateTime.now(ZoneOffset.UTC));
		
		if (exists) {
            flightRepository.deleteById(id);
        } 
		else {
            log.warn("Flight {} does not exist", id);
        }
		
		return ResponseEntity.noContent().build();
	}
	
	

}
