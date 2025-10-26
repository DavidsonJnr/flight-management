package com.flightreservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightreservation.model.dto.FlightSearchDTO;
import com.flightreservation.model.dto.FlightSearchFilters;
import com.flightreservation.model.dto.SearchPagination;
import com.flightreservation.model.request.FlightEditRequest;
import com.flightreservation.model.request.FlightRequest;
import com.flightreservation.model.response.FlightResponse;
import com.flightreservation.model.response.ResponseMessage;
import com.flightreservation.service.FlightDeleteService;
import com.flightreservation.service.FlightSaveService;
import com.flightreservation.service.FlightSearchService;
import com.flightreservation.service.FlightUpdateService;
import com.flightreservation.service.pagination.ResultPagination;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/flights")
@RequiredArgsConstructor
public class FlightController {
	
	private final FlightSaveService flightSaveService;
	private final FlightUpdateService flightUpdateService;
	private final FlightSearchService flightSearchService;
	private final FlightDeleteService flightDeleteService;
	
	@PostMapping
	public ResponseEntity<ResponseMessage<FlightResponse>> save(@RequestBody @Valid FlightRequest flightRequest) {
		return flightSaveService.save(flightRequest);
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<FlightResponse>> retrieve(@PathVariable Long id) {
        return flightSearchService.retrieve(id);
    }
	
	@GetMapping
    public ResponseEntity<ResponseMessage<ResultPagination<FlightSearchDTO>>> search(SearchPagination pagination, FlightSearchFilters filters) {
        return flightSearchService.search(pagination, filters);
    }
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseMessage<FlightResponse>> update(@PathVariable Long id, @RequestBody @Valid FlightEditRequest flightEditRequest) {
		return flightUpdateService.update(id, flightEditRequest);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		return flightDeleteService.delete(id);
	}

}
