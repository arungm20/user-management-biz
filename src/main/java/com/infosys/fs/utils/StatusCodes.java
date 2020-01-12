package com.infosys.fs.utils;

/**
 * This class contains status codes
 *
 */
public enum StatusCodes {
	// HTTP 200 status codes
	SUCCESS_RESPONSE("202", "ACCEPTED"),
	
	NO_DATA_FOUND("404", "No data found for the given input criteria."),

	// HTTP 400 status codes
	MISSING_MANDATORY_PARAMETER("400", "Missing mandatory parameter : {0}"),

	// HTTP 500 status codes
	INTERNAL_SERVER_ERROR("500", "Service encountered an unexpected condition"),
	
	// HTTP 551 status codes
	EXTERNAL_SYSTEM_CONNECTION_ISSUE("551", "Error in invoking external service interface."),
	
	// HTTP 552 status codes
	EXTERNAL_SYSTEM_UNAVAILABLE("552", "Unable to process the request due to back-end unavailability.");
	
	private String statusCode;
	
	private String statusDescription;
	
	StatusCodes(String statusCode, String statusDescription) {
		this.statusCode = statusCode;
		this.statusDescription = statusDescription;
	}
	
	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return this.statusCode;
	}
	
	/**
	 * @return the statusDescription
	 */
	public String getStatusDescription() {
		return this.statusDescription;
	}
	
}