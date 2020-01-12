package com.infosys.fs.exception;


/**
 * Exception class to return Bad Request error response 
 * (HTTP 400 status code) from the API.
 * 
 * This exception should be used when the provided request
 * cannot be processed due to reasons as such invalid 
 * request message format, missing request parameters etc
 *
 */
public class BadRequestException extends APIException {

	private static final long serialVersionUID = 1L;
	
	public BadRequestException() {
		
	}
	
	
	/**
	 * Constructor to throw Bad request exception with status code,
	 * severity and parameters.
	 * 
	 * @param statusCode
	 * @param severity
	 * @param parameters
	 */
	public BadRequestException(String statusCode, Severity severity, String... parameters) {
		super(statusCode, severity, parameters);
	}
	
	/**
	 * Constructor to throw Bad request exception with status code,
	 * status description, severity and parameters.
	 * 
	 * @param statusCode
	 * @param statusDescription
	 * @param severity
	 * @param parameters
	 */
	public BadRequestException(String statusCode, String statusDescription, Severity severity, String... parameters) {
		super(statusCode, statusDescription, severity, parameters);
	}
	
	/**
	 * Constructor to throw Bad request exception with status code,
	 * severity, previous exception trace and parameters
	 * 
	 * @param statusCode
	 * @param severity
	 * @param exception
	 * @param parameters
	 */
	public BadRequestException(String statusCode, Severity severity, Exception exception, String... parameters) {
		super(statusCode, severity, exception, parameters);
	}
	
	/**
	 * Constructor to throw Bad request exception with status code,
	 * status code, severity, previous exception trace 
	 * and parameters
	 * 
	 * @param statusCode
	 * @param statusDescription
	 * @param severity
	 * @param exception
	 * @param parameters
	 */
	public BadRequestException(String statusCode, String statusDescription, Severity severity, Exception exception, 
			String... parameters) {
		super(statusCode, statusDescription, severity, exception, parameters);
	}
}