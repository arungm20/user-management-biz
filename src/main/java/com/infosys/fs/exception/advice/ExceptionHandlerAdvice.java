package com.infosys.fs.exception.advice;

import java.text.MessageFormat;
import java.util.Iterator;

import javax.naming.ServiceUnavailableException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.infosys.fs.config.Constants;
import com.infosys.fs.exception.APIException.Severity;
import com.infosys.fs.exception.BadRequestException;
import com.infosys.fs.exception.ExternalSystemUnavailableException;
import com.infosys.fs.exception.InternalServerErrorException;
import com.infosys.fs.exception.NotFoundException;
import com.infosys.fs.model.APIResponseView;
import com.infosys.fs.utils.StatusCodes;
import com.netflix.hystrix.exception.HystrixRuntimeException;

/**
 * Advice class for exception handling for the application. This class will be
 * used to handle all the type of exceptions that can be thrown by the
 * application.
 *
 * For each type of exception, there will be specific exception handling method.
 * In each method, Error response body will be formed based on the status code,
 * severity, parameters provided in the exception.
 *
 * There will be method to handle generic Exception as well for unhandled
 * exceptions thrown at run time.
 *
 */
@ControllerAdvice("com.infosys.fs")
@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
public class ExceptionHandlerAdvice {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
	
	/**
	 * Method to handle Bad Request Exception raised for an API. Method will
	 * form the error response body for the exception raised and return the
	 * response with HTTP 400 status.
	 *
	 *
	 * @param exception
	 *            - BadRequestException object that needs to be handled.
	 * @return ResponseEntity<Object> - Response entity being returned
	 */
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequestException(BadRequestException exception) {
		
		APIResponseView responseView = this.mapResponseView(exception.getStatusCode(), exception.getStatusDescription(),
				exception.getSeverity(), exception.getParameters());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseView);
	}
	
	/**
	 * Method to handle Not Found Exception raised for an API. Method will form
	 * the error response body for the exception raised and return the response
	 * with HTTP 404 status.
	 *
	 *
	 * @param exception
	 *            - NotFoundException object that needs to be handled.
	 * @return ResponseEntity<Object> - Response entity being returned
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {
		
		APIResponseView responseView = this.mapResponseView(exception.getStatusCode(), exception.getStatusDescription(),
				exception.getSeverity(), exception.getParameters());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseView);
	}
	
	/**
	 * Method to handle Internal Server Error Exception raised for an API.
	 * Method will form the error response body for the exception raised and
	 * return the response with HTTP 500 status.
	 *
	 *
	 * @param exception
	 *            - InternalServerErrorException object that needs to be
	 *            handled.
	 * @return ResponseEntity<Object> - Response entity being returned
	 */
	@ExceptionHandler(InternalServerErrorException.class)
	public ResponseEntity<Object> handleInternalServerErrorException(InternalServerErrorException exception) {
		LOGGER.error("Internal Server Exception", exception);
		APIResponseView responseView = this.mapResponseView(exception.getStatusCode(), exception.getStatusDescription(),
				exception.getSeverity(), exception.getParameters());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseView);
	}
	
	/**
	 * Method to handle External System Unavailable Exception raised for an API.
	 * Method will form the error response body for the exception raised and
	 * return the response with HTTP 552 status.
	 *
	 *
	 * @param exception
	 *            - ExternalSystemUnavailableException object which needs to be
	 *            handled
	 * @return ResponseEntity<Object> - Response entity being returned
	 */
	@ExceptionHandler(ExternalSystemUnavailableException.class)
	public ResponseEntity<Object> handleExternalSystemUnavailableException(
			ExternalSystemUnavailableException exception) {
		
		LOGGER.error("External System Unavailable Exception", exception);
		APIResponseView responseView = this.mapResponseView(exception.getStatusCode(), exception.getStatusDescription(),
				exception.getSeverity(), exception.getParameters());
		
		return ResponseEntity.status(552).body(responseView);
	}
	
	/**
	 * Method to handle Unhandled Exception that were thrown at run time as part
	 * of API. Method will form the error response body for the exception raised
	 * and return the response with HTTP 500 status.
	 *
	 *
	 * @param exception
	 *            - Exception object that needs to be handled
	 * @return ResponseEntity<Object> - Response entity being returned
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleException(Exception exception) {
		LOGGER.error("General Exception ", exception);
		
		APIResponseView responseView = this.mapResponseView(StatusCodes.INTERNAL_SERVER_ERROR.getStatusCode(),
				StatusCodes.INTERNAL_SERVER_ERROR.getStatusDescription(), Severity.CRITICAL, exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseView);
	}
	
	/**
	 * Method to handle Hystrix RuntimeException which would be thrown when the
	 * circuit circuit is OPEN.
	 *
	 * @param exception
	 *            - HystrixRuntimeException runtime being handled
	 * @return ResponseEntity<Object> - Response entity being returned
	 */
	@ExceptionHandler(HystrixRuntimeException.class)
	public ResponseEntity<Object> handleHystrixRuntimeException(HystrixRuntimeException exception) {
		LOGGER.error("Hystrix Runtime Exception ", exception);
		LOGGER.error("Inside handleHystrixRuntimeException ", exception);
		APIResponseView responseView = this.mapResponseView(StatusCodes.EXTERNAL_SYSTEM_UNAVAILABLE.getStatusCode(),
				StatusCodes.EXTERNAL_SYSTEM_UNAVAILABLE.getStatusDescription(), Severity.CRITICAL);
		
		return ResponseEntity.status(552).body(responseView);
	}
	
	/**
	 * Method to map the APIResponseView object for exception scenarios.
	 *
	 * @param statusCode
	 *            - statusCode for failure response
	 * @param statusDescription
	 *            - Description of the status code
	 * @param severity
	 *            - Severity of the error response.
	 * @return APIResponseView - Mapped APIResonseView object
	 */
	private APIResponseView mapResponseView(String statusCode, String statusDescription, Severity severity,
			String... parameters) {
		APIResponseView response = new APIResponseView();
		response.setStatusCode(statusCode);
		response.setStatusDescription(formatErrorMessage(statusDescription, parameters));
		response.setSeverity(severity != null ? severity.name() : null);
		
		return response;
	}
	
	private static String formatErrorMessage(String statusDescription, String... parameters) {
		
		String errorMessage = null;
		if (StringUtils.isBlank(statusDescription)) {
			errorMessage = "";
		} else {
			errorMessage = MessageFormat.format(statusDescription, parameters);
		}
		return errorMessage;
	}
}
