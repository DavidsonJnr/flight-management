package com.flightreservation.model.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.flightreservation.model.enums.AirportEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "flights")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flight extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String airline;
	private String supplier;
	private BigDecimal fare;
	@Enumerated(EnumType.STRING)
	private AirportEnum departureAirport;
	@Enumerated(EnumType.STRING)
	private AirportEnum destinationAirport;
	private OffsetDateTime departureTime;
	private OffsetDateTime arrivalTime;

}
