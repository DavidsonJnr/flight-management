package com.flightreservation.exception;

public class FlightNotFoundException extends FlightException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6402190708561683484L;

	public FlightNotFoundException(String detail) {
        super("flight.not.found", detail);
    }
}
