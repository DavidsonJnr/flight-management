package com.flightreservation.model.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.flightreservation.model.enums.AirportEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class FlightRequest {
	
	@NotBlank
	private String airline;
	@NotBlank
	private String supplier;
	@NotNull
	private BigDecimal fare;
	@NotNull
	private AirportEnum departureAirport;
	@NotNull
	private AirportEnum destinationAirport;
	@NotNull
	private OffsetDateTime departureTime;
	@NotNull
	private OffsetDateTime arrivalTime;

}
