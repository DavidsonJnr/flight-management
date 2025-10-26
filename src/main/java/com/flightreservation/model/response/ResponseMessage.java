package com.flightreservation.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage<E> {

	private E responseData;
	
	@Builder
	public ResponseMessage(E responseData) {
		this.responseData = responseData;
	}

	public static <T> ResponseMessage<T> success(T responseData) {
		return buildResponseMessage(responseData);
	}
	
	public static <T> ResponseMessage<T> error(T responseData) {
		return buildResponseMessage(responseData);
	}

	private static <T> ResponseMessage<T> buildResponseMessage(T responseData) {
		return ResponseMessage.<T>builder()
				.responseData(responseData)
				.build();
	}
	
}