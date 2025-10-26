package com.flightreservation.service.pagination;

import static org.aspectj.runtime.internal.Conversions.longValue;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;

import com.flightreservation.model.dto.FlightSearchDTO;
import com.flightreservation.model.dto.FlightSearchFilters;
import com.flightreservation.model.dto.SearchPagination;
import com.flightreservation.model.enums.AirportEnum;

import jakarta.persistence.EntityManager;

public class FlightPagination extends BaseExecutePagination<FlightSearchDTO> {

    private final FlightSearchFilters filters;

    public FlightPagination(EntityManager entityManager, SearchPagination pagination, FlightSearchFilters filters) {
        super(entityManager, pagination);
        this.filters = filters;
    }

    @Override
    protected Class<FlightSearchDTO> getEntityClass() {
        return FlightSearchDTO.class;
    }

    @Override
    protected String getSelectFields() {
        return "obj.id, obj.airline, obj.supplier, obj.fare, obj.departureAirport, obj.destinationAirport, obj.departureTime, obj.arrivalTime ";
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> List<T> buildClassList(Query<?> query) {
        return (List<T>) query.setTupleTransformer(
                (tuple, aliases) -> {
                    int i = 0;
                    return FlightSearchDTO.builder()
                            .id(longValue(tuple[i++]))
                            .airline((String) tuple[i++])
                            .supplier((String) tuple[i++])
                            .fare((BigDecimal) tuple[i++])
                            .departureAirport((AirportEnum) tuple[i++])
                            .destinationAirport((AirportEnum) tuple[i++])
                            .departureTime((OffsetDateTime) tuple[i++])
                            .arrivalTime((OffsetDateTime) tuple[i++])
                            .build();
                }
        ).list();
    }

    @Override
    public String getBaseQuery(String select) {

        StringBuilder hql = new StringBuilder("SELECT ").append(select)
                .append(" FROM Flight obj ")
                .append(" WHERE 1=1 ");

        if (Objects.nonNull(filters.getOrigin())) {
        	hql.append("AND obj.departureAirport = :ORIGIN ");
        }
        if (Objects.nonNull(filters.getDestination())) {
        	hql.append("AND obj.destinationAirport = :DESTINATION ");
        }
        if (StringUtils.isNotBlank(filters.getAirline())) {
            hql.append("AND LOWER(obj.airline) LIKE LOWER(CONCAT('%', :AIRLINE, '%')) ");
        }
        if(Objects.nonNull(filters.getDepartureStart()) && Objects.nonNull(filters.getDepartureEnd())) {
            hql.append("AND obj.departureTime BETWEEN :DEPARTURESTART AND :DEPARTUREEND ");
        }
        else if(Objects.nonNull(filters.getDepartureStart())) {
            hql.append("AND obj.departureTime >= :DEPARTURESTART ");
        }
        else if(Objects.nonNull(filters.getDepartureEnd())) {
            hql.append("AND obj.departureTime <= :DEPARTUREEND ");
        }
        
        if(Objects.nonNull(filters.getArrivalStart()) && Objects.nonNull(filters.getArrivalEnd())) {
            hql.append("AND obj.arrivalTime BETWEEN :ARRIVALSTART AND :ARRIVALEND ");
        }
        else if(Objects.nonNull(filters.getArrivalStart())) {
            hql.append("AND obj.arrivalTime >= :ARRIVALSTART ");
        }
        else if(Objects.nonNull(filters.getArrivalEnd())) {
            hql.append("AND obj.arrivalTime <= :ARRIVALEND ");
        }

        return hql.toString();
    }

    @Override
    public void setParameters(jakarta.persistence.Query query) {

    	if (Objects.nonNull(filters.getOrigin())) {
    		query.setParameter("ORIGIN", filters.getOrigin());
        }
        if (Objects.nonNull(filters.getDestination())) {
        	query.setParameter("DESTINATION", filters.getDestination());
        }
        if (StringUtils.isNotBlank(filters.getAirline())) {
        	query.setParameter("AIRLINE", filters.getAirline());
        }
        if(Objects.nonNull(filters.getDepartureStart()) && Objects.nonNull(filters.getDepartureEnd())) {
            query.setParameter("DEPARTURESTART", filters.getDepartureStart());
            query.setParameter("DEPARTUREEND", filters.getDepartureEnd());
        }
        else if (Objects.nonNull(filters.getDepartureStart())) {
        	query.setParameter("DEPARTURESTART", filters.getDepartureStart());
        }
        else if (Objects.nonNull(filters.getDepartureEnd())) {
        	query.setParameter("DEPARTUREEND", filters.getDepartureEnd());
        }
        
        if(Objects.nonNull(filters.getArrivalStart()) && Objects.nonNull(filters.getArrivalEnd())) {
        	query.setParameter("ARRIVALSTART", filters.getArrivalStart());
            query.setParameter("ARRIVALEND", filters.getArrivalEnd());
        }
        else if(Objects.nonNull(filters.getArrivalStart())) {
        	query.setParameter("ARRIVALSTART", filters.getArrivalStart());
        }
        else if(Objects.nonNull(filters.getArrivalEnd())) {
        	query.setParameter("ARRIVALEND", filters.getArrivalEnd());
        }
    }
}
