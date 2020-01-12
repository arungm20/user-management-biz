package com.infosys.fs.exception;


public class ExternalSystemUnavailableException extends APIException {

	private static final long serialVersionUID = 1L;
	
	public ExternalSystemUnavailableException() {
		
	}
	
	
	/**
	 * Constructor to throw External System unavailable exception with status code,
	 * severity and parameters.
	 * 
	 * @param statusCode
	 * @param severity
	 * @param parameters
	 */
	public ExternalSystemUnavailableException(String statusCode, Severity severity, String... parameters) {
		super(statusCode, severity, parameters);
	}
	
	/**
	 * Constructor to throw External System unavailable exception with status code,
	 * status description, severity and parameters.
	 * 
	 * @param statusCode
	 * @param statusDescription
	 * @param severity
	 * @param parameters
	 */
	public ExternalSystemUnavailableException(String statusCode, String statusDescription, Severity severity, 
			String... parameters) {
		super(statusCode, statusDescription, severity, parameters);
	}
	
	/**
	 * Constructor to throw External System unavailable exception with status code,
	 * severity, previous exception trace and parameters
	 * 
	 * @param statusCode
	 * @param severity
	 * @param exception
	 * @param parameters
	 */
	public ExternalSystemUnavailableException(String statusCode, Severity severity, Exception exception, 
			String... parameters) {
		super(statusCode, severity, exception, parameters);
	}
	
	/**
	 * Constructor to throw External System unavailable exception with status code,
	 * status code, severity, previous exception trace 
	 * and parameters
	 * 
	 * @param statusCode
	 * @param statusDescription
	 * @param severity
	 * @param exception
	 * @param parameters
	 */
	public ExternalSystemUnavailableException(String statusCode, String statusDescription, Severity severity, 
			Exception exception, String... parameters) {
		super(statusCode, statusDescription, severity, exception, parameters);
	}
}
