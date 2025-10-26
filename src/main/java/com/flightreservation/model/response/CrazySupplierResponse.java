package com.flightreservation.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrazySupplierResponse {

	private String carrier;
    private BigDecimal basePrice;
    private BigDecimal tax;
    private String departureAirportName;
    private String arrivalAirportName;
    private LocalDateTime outboundDateTime;
    private LocalDateTime inboundDateTime;
	
}
