package com.flightreservation.exception;

public class FlightException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 147463989826280741L;
	
	private final String messageKey;
    private final String detail;

    public FlightException(String messageKey, String detail) {
        super(detail);
        this.messageKey = messageKey;
        this.detail = detail;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getDetail() {
        return detail;
    }
    
}
