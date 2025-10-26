package com.flightreservation.model.response;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.flightreservation.model.enums.ResponseType;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightErrorResponse {
	
	private ResponseType type;
	private String messageKey;
	private String detail;
	private Object[] info;
	private OffsetDateTime timestamp;
	
	public FlightErrorResponse(ResponseType type, String messageKey, String detail, OffsetDateTime timestamp) {
		this.type = type;
		this.messageKey = messageKey;
		this.detail = detail;
		this.timestamp = timestamp;
	}
	
	public FlightErrorResponse(ResponseType type, String messageKey, String detail, Object[] info, OffsetDateTime timestamp) {
		this.type = type;
		this.messageKey = messageKey;
		this.detail = detail;
		this.info = info;
		this.timestamp = timestamp;
	}
	
	

}
