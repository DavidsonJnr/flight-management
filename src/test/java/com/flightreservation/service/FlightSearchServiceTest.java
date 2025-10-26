package com.flightreservation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.flightreservation.integration.CrazySupplierApiService;
import com.flightreservation.model.dto.FlightSearchDTO;
import com.flightreservation.model.dto.FlightSearchFilters;
import com.flightreservation.model.dto.SearchPagination;
import com.flightreservation.model.enums.AirportEnum;
import com.flightreservation.model.response.ResponseMessage;
import com.flightreservation.repository.flight.FlightRepository;
import com.flightreservation.service.pagination.ResultPagination;

@ExtendWith(MockitoExtension.class)
class FlightSearchServiceTest {

	@Mock
    private FlightRepository flightRepository;

    @Mock
    private CrazySupplierApiService crazySupplierApiService;

    @InjectMocks
    private FlightSearchService flightSearchService;

    @Test
    void testSearchCombinesDbAndApiFlights() {
        SearchPagination pagination = new SearchPagination();
        FlightSearchFilters filters = new FlightSearchFilters();
        filters.setOrigin(AirportEnum.LIS);
        filters.setDestination(AirportEnum.MAD);
        filters.setDepartureStart(OffsetDateTime.now());
        filters.setDepartureEnd(OffsetDateTime.now().plusDays(1));

        ResultPagination<FlightSearchDTO> dbPagination = ResultPagination.<FlightSearchDTO>builder()
                .elements(List.of(FlightSearchDTO.builder().airline("Iberia").departureTime(OffsetDateTime.now()).build()))
                .totalElements(1L)
                .pagination(pagination)
                .build();

        FlightSearchDTO apiFlight = FlightSearchDTO.builder().airline("TAP").departureTime(OffsetDateTime.now().plusHours(1)).build();

        when(flightRepository.searchAll(pagination, filters)).thenReturn(dbPagination);
        when(crazySupplierApiService.findFlights(any())).thenReturn(List.of(apiFlight));

        ResponseEntity<ResponseMessage<ResultPagination<FlightSearchDTO>>> response = flightSearchService.search(pagination, filters);

        List<FlightSearchDTO> combinedFlights = response.getBody().getResponseData().getElements();
        assertEquals(2, combinedFlights.size());
        assertEquals("Iberia", combinedFlights.get(0).getAirline());
        assertEquals("TAP", combinedFlights.get(1).getAirline());
    }

}
