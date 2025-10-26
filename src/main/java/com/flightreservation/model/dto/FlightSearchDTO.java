package com.flightreservation.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.flightreservation.model.enums.AirportEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearchDTO {
	
	private Long id;
	private String airline; 
	private String supplier; 
	private BigDecimal fare; 
	private AirportEnum departureAirport; 
	private AirportEnum destinationAirport; 
	private OffsetDateTime departureTime; 
	private OffsetDateTime arrivalTime;

}
