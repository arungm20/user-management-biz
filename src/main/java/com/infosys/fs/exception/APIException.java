package com.infosys.fs.exception;





public class APIException extends Exception {
	
	public enum Severity {

		INFORMATION,
		WARNING,
		ERROR,
		CRITICAL
	}

	
	private static final long serialVersionUID = 1L;
	private String statusCode;
	private String statusDescription;
	
	private Severity severity;
	private String parameters[];
	
	public APIException() {
		
	}
	
	/**
	 * Constructor to instantiate status code,
	 * severity and parameters.
	 * 
	 * @param statusCode
	 * @param severity
	 * @param parameters
	 */
	public APIException(String statusCode, Severity severity, String... parameters) {
		this.statusCode = statusCode;
		this.severity = severity;
		this.parameters = parameters;
	}
	
	/**
	 * Constructor to instantiate status code, status description
	 * severity and parameters.
	 * 
	 * @param statusCode
	 * @param statusDescription
	 * @param severity
	 * @param parameters
	 */
	public APIException(String statusCode, String statusDescription, Severity severity, String... parameters) {
		this.statusCode = statusCode;
		this.statusDescription = statusDescription;
		this.severity = severity;
		this.parameters = parameters;
	}
	
	/**
	 * Constructor to instantiate status code,
	 * severity, previous exception trace and parameters
	 * 
	 * @param statusCode
	 * @param severity
	 * @param exception
	 * @param parameters
	 */
	public APIException(String statusCode, Severity severity, Exception exception, String... parameters) {
		super(exception);
		this.statusCode = statusCode;
		this.severity = severity;
		this.parameters = parameters;
	}
	
	/**
	 * Constructor to instantiate status code, status description,
	 * severity, previous exception trace and parameters
	 * 
	 * @param statusCode
	 * @param statusDescription
	 * @param severity
	 * @param exception
	 * @param parameters
	 */
	public APIException(String statusCode, String statusDescription, Severity severity, Exception exception, String... parameters) {
		super(exception);
		this.statusCode = statusCode;
		this.statusDescription = statusDescription;
		this.severity = severity;
		this.parameters = parameters;
	}
	

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the statusDescription
	 */
	public String getStatusDescription() {
		return statusDescription;
	}

	/**
	 * @param statusDescription the statusDescription to set
	 */
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	/**
	 * @return the severity
	 */
	public Severity getSeverity() {
		return severity;
	}

	/**
	 * @param severity the severity to set
	 */
	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	/**
	 * @return the parameters
	 */
	public String[] getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}

}
