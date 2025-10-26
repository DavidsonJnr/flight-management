package com.flightreservation.repository.flight;

import org.springframework.transaction.annotation.Transactional;

import com.flightreservation.model.dto.FlightSearchDTO;
import com.flightreservation.model.dto.FlightSearchFilters;
import com.flightreservation.model.dto.SearchPagination;
import com.flightreservation.service.pagination.FlightPagination;
import com.flightreservation.service.pagination.ResultPagination;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional(readOnly = true)
public class FlightRepositoryReadCustomImpl implements FlightRepositoryReadCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public ResultPagination<FlightSearchDTO> searchAll(SearchPagination pagination, FlightSearchFilters filters) {
		FlightPagination sessionsPagination = new FlightPagination(this.entityManager, pagination, filters);

		return ResultPagination.<FlightSearchDTO>builder()
				.totalElements(sessionsPagination.count())
				.elements(sessionsPagination.result())
				.pagination(pagination)
				.build();
	}

}
