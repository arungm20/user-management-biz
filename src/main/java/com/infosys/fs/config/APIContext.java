package com.infosys.fs.config;

import java.util.HashMap;
import java.util.Map;

public class APIContext {
	
	//Holds country code
	private String country = "";
	
	//Holds channel invoking the service
	private String channel = "";
	
	//Holds URI of the service invoked
	private String uri = "";
	
	//Holds the method type
	private String method = "";
	
	//Holds the boolean value
	private boolean booleanValue;
	
	private String serviceName = "";
	
	private Map<String,String> headerParams = new HashMap<String,String>();

	private Map<String,String> pathParams = new HashMap<String,String>();
	
	private Map<String,String> queryParams = new HashMap<String,String>();
	
	public APIContext() {
		
	}
	
	public APIContext(String channel, String uri, String method) {
		this.channel = channel;
		this.uri = uri;
		this.method = method;
	}
	
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the headerParams
	 */
	public Map<String,String> getHeaderParams() {
		return headerParams;
	}

	/**
	 * @return the pathParams
	 */
	public Map<String,String> getPathParams() {
		if(pathParams == null){
			pathParams = new HashMap<String,String>();
		}
		return pathParams;
	}

	/**
	 * @return the queryParams
	 */
	public Map<String,String> getQueryParams() {
		if(queryParams == null){
			queryParams = new HashMap<String,String>();
		}
		return queryParams;
	}

	/**
	 * @param headerParams the headerParams to set
	 */
	public void setHeaderParams(Map<String,String> headerParams) {
		if(headerParams == null){
			headerParams = new HashMap<String,String>();
		}
		this.headerParams = headerParams;
	}

	/**
	 * @param pathParams the pathParams to set
	 */
	public void setPathParams(Map<String,String> pathParams) {
		this.pathParams = pathParams;
	}

	/**
	 * @param queryParams the queryParams to set
	 */
	public void setQueryParams(Map<String,String> queryParams) {
		this.queryParams = queryParams;
	}

	/**
	 * @return the booleanValue
	 */
	public boolean isBooleanValue() {
		return booleanValue;
	}

	/**
	 * @param booleanValue the booleanValue to set
	 */
	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

}
