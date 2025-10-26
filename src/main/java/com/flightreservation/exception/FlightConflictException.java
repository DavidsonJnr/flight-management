package com.flightreservation.exception;

public class FlightConflictException extends FlightException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1949058200293427005L;

	public FlightConflictException(String detail) {
        super("flight.version.conflict", detail);
    }

}
