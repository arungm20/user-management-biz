package com.infosys.fs.exception;

/**
 * Exception class to return Internal Server Error response (HTTP 500 status
 * code) from the API.
 * 
 * This exception should be used when the service/API encountered an unexpected
 * condition which prevents the service to fulfil the request.
 *
 */
public class InternalServerErrorException extends APIException {
	
	private static final long serialVersionUID = 1L;
	
	public InternalServerErrorException() {
		
	}
	
	/**
	 * Constructor to throw Server exception with status code, severity and
	 * parameters.
	 * 
	 * @param statusCode
	 * @param severity
	 * @param parameters
	 */
	public InternalServerErrorException(String statusCode, Severity severity, String... parameters) {
		super(statusCode, severity, parameters);
	}
	
	/**
	 * Constructor to throw Server exception with status code, status
	 * description, severity and parameters.
	 * 
	 * @param statusCode
	 * @param statusDescription
	 * @param severity
	 * @param parameters
	 */
	public InternalServerErrorException(String statusCode, String statusDescription, Severity severity,
			String... parameters) {
		super(statusCode, statusDescription, severity, parameters);
	}
	
	/**
	 * Constructor to throw Server exception with status code, severity,
	 * previous exception trace and parameters
	 * 
	 * @param statusCode
	 * @param severity
	 * @param exception
	 * @param parameters
	 */
	public InternalServerErrorException(String statusCode, Severity severity, Exception exception,
			String... parameters) {
		super(statusCode, severity, exception, parameters);
	}
	
	/**
	 * Constructor to throw Server exception with status code, status code,
	 * severity, previous exception trace and parameters
	 * 
	 * @param statusCode
	 * @param statusDescription
	 * @param severity
	 * @param exception
	 * @param parameters
	 */
	public InternalServerErrorException(String statusCode, String statusDescription, Severity severity,
			Exception exception, String... parameters) {
		super(statusCode, statusDescription, severity, exception, parameters);
	}
}
