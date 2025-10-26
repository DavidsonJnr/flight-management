package com.flightreservation.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightreservation.exception.FlightNotFoundException;
import com.flightreservation.integration.CrazySupplierApiService;
import com.flightreservation.model.dto.FlightSearchDTO;
import com.flightreservation.model.dto.FlightSearchFilters;
import com.flightreservation.model.dto.SearchPagination;
import com.flightreservation.model.request.CrazySupplierRequest;
import com.flightreservation.model.response.FlightResponse;
import com.flightreservation.model.response.ResponseMessage;
import com.flightreservation.repository.flight.FlightRepository;
import com.flightreservation.service.pagination.ResultPagination;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightSearchService {

	private final FlightRepository flightRepository;
	private final CrazySupplierApiService crazySupplierApiService;
	
	public ResponseEntity<ResponseMessage<ResultPagination<FlightSearchDTO>>> search(SearchPagination pagination, FlightSearchFilters filters) {
		
		ResultPagination<FlightSearchDTO> searchAll = flightRepository.searchAll(pagination, filters);
		
		List<FlightSearchDTO> flightsFromApi = this.findFlightsFromApi(filters);
		
		List<FlightSearchDTO> elements = searchAll.getElements();
		
		List<FlightSearchDTO> allFlights = Stream.concat(elements.stream(), flightsFromApi.stream())
                .sorted(Comparator.comparing(FlightSearchDTO::getDepartureTime))
                .limit(pagination.getSize())
                .toList();
		
		long totalElements = elements.size() + flightsFromApi.size();
		
		ResultPagination<FlightSearchDTO> resultPagination = ResultPagination.<FlightSearchDTO>builder()
	        .totalElements(totalElements)
	        .elements(allFlights)
	        .pagination(pagination)
	        .build();
		
		return ResponseEntity.ok().body(ResponseMessage.success(resultPagination));
	}

	private List<FlightSearchDTO> findFlightsFromApi(FlightSearchFilters filters) {
		List<FlightSearchDTO> flightsFromApi = new ArrayList<>();
		if(Objects.nonNull(filters.getOrigin()) && Objects.nonNull(filters.getDestination()) && Objects.nonNull(filters.getDepartureStart()) && Objects.nonNull(filters.getDepartureEnd())) {
			
			CrazySupplierRequest crazySupplierRequest = CrazySupplierRequest.builder()
				.from(filters.getOrigin().name())
				.to(filters.getDestination().name())
				.outboundDate(filters.getDepartureStart().toLocalDate())
				.inboundDate(filters.getDepartureEnd().toLocalDate())
				.build();
			
			flightsFromApi = crazySupplierApiService.findFlights(crazySupplierRequest);
		}
		
		return flightsFromApi;
	}

	public ResponseEntity<ResponseMessage<FlightResponse>> retrieve(Long id) {
		
		FlightResponse flightResponse = flightRepository.retrieveFlightById(id)
	            .orElseThrow(() -> new FlightNotFoundException("Flight not found"));
		
		return ResponseEntity.ok().body(ResponseMessage.success(flightResponse));
	}

}
