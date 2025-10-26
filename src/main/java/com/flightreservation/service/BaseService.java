package com.flightreservation.service;

import java.time.OffsetDateTime;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.flightreservation.exception.FlightConflictException;
import com.flightreservation.exception.FlightException;
import com.flightreservation.model.entity.Flight;
import com.flightreservation.model.response.FlightResponse;

public abstract class BaseService {
	
	protected void validateFlightTimes(OffsetDateTime departureTime, OffsetDateTime arrivalTime) {
        if (!arrivalTime.isAfter(departureTime)) {
            throw new FlightException("arrival.before.departure", "Arrival time must be after departure time");
        }
    }
	
	protected void validateVersion(Long entityVersion, Long requestVersion) {
        if (!Objects.equals(entityVersion, requestVersion)) {
            throw new FlightConflictException("Entity version is wrong");
        }
    }
	
	protected FlightResponse toResponse(Flight entity) {
        FlightResponse response = new FlightResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

}
