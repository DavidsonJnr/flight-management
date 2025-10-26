package com.flightreservation.repository.flight;

import com.flightreservation.model.dto.FlightSearchDTO;
import com.flightreservation.model.dto.FlightSearchFilters;
import com.flightreservation.model.dto.SearchPagination;
import com.flightreservation.service.pagination.ResultPagination;

public interface FlightRepositoryReadCustom {
	
	ResultPagination<FlightSearchDTO> searchAll(SearchPagination pagination, FlightSearchFilters filters);

}
