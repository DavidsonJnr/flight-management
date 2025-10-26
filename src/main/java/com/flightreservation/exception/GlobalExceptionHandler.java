package com.flightreservation.exception;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flightreservation.model.enums.ResponseType;
import com.flightreservation.model.response.FlightErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(FlightException.class)
    public ResponseEntity<FlightErrorResponse> handleFlightException(FlightException ex) {
		return handleBadRequest(ex);
    }
	
	@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<FlightErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		return handleBadRequest(ex);
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<FlightErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		
		Object[] fieldErrors = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .toArray();
		
	    FlightErrorResponse error = new FlightErrorResponse(
	            ResponseType.ERROR,
	            "validation.failed",
	            "Validation failed",
	            fieldErrors,
	            OffsetDateTime.now()
	    );

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<FlightErrorResponse> handleNotFound(FlightNotFoundException ex) {
        FlightErrorResponse error = new FlightErrorResponse(
                ResponseType.ERROR,
                "flight.not.found",
                ex.getMessage(),
                OffsetDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
	
	@ExceptionHandler(FlightConflictException.class)
    public final ResponseEntity<FlightErrorResponse> handleLiveNotFoundException(FlightConflictException ex) {
		FlightErrorResponse error = new FlightErrorResponse(
                ResponseType.ERROR,
                "flight.version.conflict",
                ex.getMessage(),
                OffsetDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<FlightErrorResponse> handleGenericException(Exception ex) {
		FlightErrorResponse error = new FlightErrorResponse(
                ResponseType.ERROR,
                "internal.error",
                ex.getMessage(),
                OffsetDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
	
	private ResponseEntity<FlightErrorResponse> handleBadRequest(Exception ex) {
		FlightErrorResponse error = new FlightErrorResponse(
                ResponseType.ERROR,
                "bad.request",
                ex.getMessage(),
                OffsetDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
