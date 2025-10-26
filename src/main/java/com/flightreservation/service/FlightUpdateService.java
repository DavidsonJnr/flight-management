package com.flightreservation.service;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightreservation.exception.FlightNotFoundException;
import com.flightreservation.model.entity.Flight;
import com.flightreservation.model.request.FlightEditRequest;
import com.flightreservation.model.response.FlightResponse;
import com.flightreservation.model.response.ResponseMessage;
import com.flightreservation.repository.flight.FlightRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightUpdateService extends BaseService {

	private final FlightRepository flightRepository;

	@Transactional
	public ResponseEntity<ResponseMessage<FlightResponse>> update(Long id, FlightEditRequest flightEditRequest) {

		super.validateFlightTimes(flightEditRequest.getDepartureTime(), flightEditRequest.getArrivalTime());
		
		Flight flight = flightRepository.findById(id)
				.orElseThrow(() -> new FlightNotFoundException("Flight not found"));

		super.validateVersion(flight.getVersion(), flightEditRequest.getVersion());

		BeanUtils.copyProperties(flightEditRequest, flight, "id", "createdAt", "createdBy", "version");

		Flight updated = flightRepository.saveAndFlush(flight);
		
		return ResponseEntity.ok(ResponseMessage.success(super.toResponse(updated)));
	}

}
