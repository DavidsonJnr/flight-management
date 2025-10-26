package com.flightreservation.service.pagination;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flightreservation.model.dto.SearchPagination;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResultPagination<T> {

    private Long totalElements;
    private Long totalPages;
    private Integer page;
    private List<T> elements;

    public ResultPagination() {
    }

    @JsonIgnore
    private SearchPagination pagination;

    @Builder
    public ResultPagination(Long totalElements, List<T> elements, SearchPagination pagination) {
        this.totalElements = totalElements;
        this.elements = elements;
        this.pagination = pagination;
    }

    public Integer getPage() {
        return pagination.getPage();
    }

    public Long getTotalPages() {
        return (long) Math.ceil((double) totalElements / pagination.getSize());
    }
}
