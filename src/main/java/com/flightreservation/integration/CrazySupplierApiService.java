package com.flightreservation.integration;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.flightreservation.model.dto.FlightSearchDTO;
import com.flightreservation.model.enums.AirportEnum;
import com.flightreservation.model.request.CrazySupplierRequest;
import com.flightreservation.model.response.CrazySupplierResponse;
import com.flightreservation.util.DateUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrazySupplierApiService {

	private final ExternalApiClient externalApiClient;
	private static final String BASE_URL = "https://api.crazy-supplier.com";
	private static final String ZONE_ID_API = "CET";

	public List<FlightSearchDTO> findFlights(CrazySupplierRequest crazySupplierRequest) {

		try {

			List<CrazySupplierResponse> flightList = externalApiClient.post(BASE_URL, "/flights", crazySupplierRequest,
					CrazySupplierResponse.class, Duration.ofSeconds(5));

			if (flightList == null || flightList.isEmpty()) {
				return Collections.emptyList();
			}

			return flightList.stream().map(this::mapToFlightResponse).collect(Collectors.toList());

		} catch (WebClientRequestException | WebClientResponseException ex) {
			log.error("Error find flights from CrazySupplier: {}", ex.getMessage());
			return Collections.emptyList();
		} catch (Exception ex) {
			log.error("Error in CrazySupplierApiService: {}", ex.getMessage());
			return Collections.emptyList();
		}
	}

	private FlightSearchDTO mapToFlightResponse(CrazySupplierResponse crazySupplierResponse) {
		FlightSearchDTO response = new FlightSearchDTO();
		response.setAirline(crazySupplierResponse.getCarrier());
		response.setSupplier("crazySupplierAPI");
		response.setFare(crazySupplierResponse.getBasePrice().add(crazySupplierResponse.getTax()));
		response.setDepartureAirport(AirportEnum.valueOf(crazySupplierResponse.getDepartureAirportName()));
		response.setDestinationAirport(AirportEnum.valueOf(crazySupplierResponse.getArrivalAirportName()));
		response.setDepartureTime(DateUtil.convertCETtoUTC(ZONE_ID_API, crazySupplierResponse.getOutboundDateTime()));
		response.setArrivalTime(DateUtil.convertCETtoUTC(ZONE_ID_API, crazySupplierResponse.getInboundDateTime()));
		return response;
	}

}
