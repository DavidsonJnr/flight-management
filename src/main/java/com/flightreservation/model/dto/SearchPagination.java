package com.flightreservation.model.dto;

import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchPagination {
	
	private int page = 1;
	private int size = 10;
	private Sort.Direction sort = Sort.Direction.ASC;
	private String sortField = "id";
	
	@JsonIgnore
	public Integer getOffset() {
		int offset = (page - 1) * size;
		return Math.max(offset, 0);
	}
}