package com.flightreservation.service;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightreservation.model.entity.Flight;
import com.flightreservation.model.request.FlightRequest;
import com.flightreservation.model.response.FlightResponse;
import com.flightreservation.model.response.ResponseMessage;
import com.flightreservation.repository.flight.FlightRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightSaveService extends BaseService {
	
	private final FlightRepository flightRepository;

	public ResponseEntity<ResponseMessage<FlightResponse>> save(FlightRequest flightRequest) {
		
		super.validateFlightTimes(flightRequest.getDepartureTime(), flightRequest.getArrivalTime());
		
		Flight flight = new Flight();
		BeanUtils.copyProperties(flightRequest, flight);
		
		flightRepository.save(flight);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMessage.success(super.toResponse(flight)));
	}

}
