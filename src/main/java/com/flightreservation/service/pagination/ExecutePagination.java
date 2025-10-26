package com.flightreservation.service.pagination;

import java.util.List;

import com.flightreservation.model.dto.SearchPagination;

import jakarta.persistence.Query;

public interface ExecutePagination<T> {

	Long count();

	List<T> result();

	String order(SearchPagination pagination);

	String getBaseQuery(String select);

	default void setParameters(Query query) {}
}