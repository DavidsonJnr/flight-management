package com.flightreservation.model.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.flightreservation.model.enums.AirportEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class FlightResponse {

	private Long id;
	private Long version;
	private String airline;
	private String supplier;
	private BigDecimal fare;
	private AirportEnum departureAirport;
	private AirportEnum destinationAirport;
	private OffsetDateTime departureTime;
	private OffsetDateTime arrivalTime;
	
	public FlightResponse(Long id, Long version, String airline, String supplier, BigDecimal fare,
			AirportEnum departureAirport, AirportEnum destinationAirport, OffsetDateTime departureTime,
			OffsetDateTime arrivalTime) {
		this.id = id;
		this.version = version;
		this.airline = airline;
		this.supplier = supplier;
		this.fare = fare;
		this.departureAirport = departureAirport;
		this.destinationAirport = destinationAirport;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}
	
	
	
}
