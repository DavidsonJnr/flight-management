package com.flightreservation.repository.flight;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flightreservation.model.entity.Flight;
import com.flightreservation.model.response.FlightResponse;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>, FlightRepositoryReadCustom {

	@Query("""
		    SELECT COUNT(obj) > 0 
		    FROM Flight obj 
		    WHERE obj.id = :ID AND obj.departureTime > :DATENOW
		""")
	boolean existsFutureFlightById(@Param("ID") Long id, @Param("DATENOW") OffsetDateTime now);

	@Query("""
		    SELECT new com.flightreservation.model.response.FlightResponse(obj.id, obj.version, obj.airline, obj.supplier, obj.fare, obj.departureAirport, obj.destinationAirport, obj.departureTime, obj.arrivalTime) 
		    FROM Flight obj 
		    WHERE obj.id = :ID
		""")
	Optional<FlightResponse> retrieveFlightById(@Param("ID") Long id);


}
