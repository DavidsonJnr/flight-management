package com.flightreservation.model.dto;

import java.time.OffsetDateTime;

import com.flightreservation.model.enums.AirportEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightSearchFilters {

	private AirportEnum origin;
	private AirportEnum destination;
	private String airline;
	private OffsetDateTime departureStart;
	private OffsetDateTime departureEnd;
	private OffsetDateTime arrivalStart;
	private OffsetDateTime arrivalEnd;

}
