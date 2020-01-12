package com.infosys.fs.exception;


/**
 * Exception class to return Not Found error response 
 * (HTTP 404 status code) from the API.
 * 
 * This exception should be used when the service/API
 * was not able to find what was requested.
 *
 */
public class NotFoundException extends APIException {

	private static final long serialVersionUID = 1L;
	
	public NotFoundException() {
		
	}
	
	
	/**
	 * Constructor to throw Not Found exception with status code,
	 * severity and parameters.
	 * 
	 * @param statusCode
	 * @param severity
	 * @param parameters
	 */
	public NotFoundException(String statusCode, Severity severity, String... parameters) {
		super(statusCode, severity, parameters);
	}
	
	/**
	 * Constructor to throw Not Found exception with status code,
	 * status description, severity and parameters.
	 * 
	 * @param statusCode
	 * @param statusDescription
	 * @param severity
	 * @param parameters
	 */
	public NotFoundException(String statusCode, String statusDescription, Severity severity, String... parameters) {
		super(statusCode, statusDescription, severity, parameters);
	}
	
	/**
	 * Constructor to throw Not Found exception with status code,
	 * severity, previous exception trace and parameters
	 * 
	 * @param statusCode
	 * @param severity
	 * @param exception
	 * @param parameters
	 */
	public NotFoundException(String statusCode, Severity severity, Exception exception, String... parameters) {
		super(statusCode, severity, exception, parameters);
	}
	
	/**
	 * Constructor to throw Not Found exception with status code,
	 * status code, severity, previous exception trace 
	 * and parameters
	 * 
	 * @param statusCode
	 * @param statusDescription
	 * @param severity
	 * @param exception
	 * @param parameters
	 */
	public NotFoundException(String statusCode, String statusDescription, Severity severity, Exception exception, 
			String... parameters) {
		super(statusCode, statusDescription, severity, exception, parameters);
	}
}
