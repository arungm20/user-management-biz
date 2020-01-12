package com.infosys.fs.config;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.logging.log4j.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.infosys.fs.config.HttpClientConfig.HttpHostConfiguration;
import com.infosys.fs.exception.ExternalSystemUnavailableException;
import com.infosys.fs.exception.InternalServerErrorException;
import com.infosys.fs.exception.NotFoundException;
import com.infosys.fs.model.APIResponseView;

import io.undertow.util.BadRequestException;
import org.apache.commons.lang3.StringUtils;

/**
 * This class is used to invoke the rest api call
 *
 */
@Configuration
public class CustomRestTemplate {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomRestTemplate.class);
	
	@Autowired
	private HttpClientConfig httpClientConfig;
	
	@Value("${http.request.config.connectionRequestTimeout}")
	private int connectionRequestTimeout;
	
	@Value("${http.request.config.connectTimeout}")
	private int connectTimeout;
	
	@Value("${http.request.config.socketTimeout}")
	private int socketTimeout;
	
	@Value("${http.request.config.keepAliveTime}")
	private int keepAliveTime;
	
	/**
	 * Method to create RestTemplate bean with the given HTTP Request Factory
	 * and HTTP Client.
	 *
	 * @param httpClient
	 *            - HTTP Client interface that will be used to make the
	 *            HTTP/HTTPS calls.
	 * @param requestFactory
	 *            - Request Factory that will use HTTP Client to use the timeout
	 *            configurations, connection pooling defined for the client.
	 *
	 * @return RestTemplate - RestTemplate Bean.
	 */
	@Bean
	RestTemplate restTemplate(HttpClient httpClient) {
		LOGGER.info("Custom Rest Template is created");
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		// restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
		return restTemplate;
	}
	
	/**
	 * This method is used create Pooling connection manager
	 *
	 * @return PoolingHttpClientConnectionManager
	 */
	@Bean
	public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
		PoolingHttpClientConnectionManager conn = new PoolingHttpClientConnectionManager();
		conn.setMaxTotal(this.httpClientConfig.getMaxTotal());
		// Default max per route is used in case it's not set for a specific
		// route
		conn.setDefaultMaxPerRoute(this.httpClientConfig.getDefaultMaxPerRoute());
		// and / or
		if (CollectionUtils.isNotEmpty(this.httpClientConfig.getMaxPerRoutes())) {
			for (HttpHostConfiguration httpHostConfig : this.httpClientConfig.getMaxPerRoutes()) {
				HttpHost host = new HttpHost(httpHostConfig.getHost(), httpHostConfig.getPort(),
						httpHostConfig.getScheme());
				// Max per route for a specific host route
				conn.setMaxPerRoute(new HttpRoute(host), httpHostConfig.getMaxPerRoute());
			}
		}
		return conn;
	}
	
	/**
	 * This method is used to create RequestConfig
	 *
	 * @return RequestConfig
	 */
	@Bean
	public RequestConfig requestConfig() {
		return RequestConfig.custom().setConnectionRequestTimeout(this.connectionRequestTimeout)
				.setConnectTimeout(this.connectTimeout).setSocketTimeout(this.socketTimeout).build();
	}
	
	/**
	 * This method is used to create http client
	 *
	 * @param poolingHttpClientConnectionManager
	 *            - PoolingHttpClientConnectionManager object
	 * @param requestConfig
	 *            - RequestConfig object
	 * @return CloseableHttpClient
	 */
	@Bean
	public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,
			RequestConfig requestConfig) {
		return HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager)
				.setDefaultRequestConfig(requestConfig).setKeepAliveStrategy(connectionKeepAliveStrategy()).build();
	}
	
	// Interface for deciding how long a connection can remain idle before being
	// reused.
	/**
	 * This method is used to create connection
	 *
	 * @return ConnectionKeepAliveStrategy
	 */
	@Bean
	public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
		return new ConnectionKeepAliveStrategy() {
			
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				HeaderElementIterator it = new BasicHeaderElementIterator(
						response.headerIterator(HTTP.CONN_KEEP_ALIVE));
				while (it.hasNext()) {
					HeaderElement he = it.nextElement();
					String param = he.getName();
					String value = he.getValue();
					
					if ((value != null) && StringUtils.equalsIgnoreCase(param, "timeout")) {
						return Long.parseLong(value) * 1000;
					}
				}
				return CustomRestTemplate.this.keepAliveTime;
			}
		};
	}
	
	/**
	 * Method to invoke REST API calls. Method will take header, query param(s)
	 * in request along with the URL, acceptable media types and the HTTP method
	 * to be invoked. Also if the API to be invoked involves request body,
	 * request body can also be provided in input.
	 *
	 * responseClass should be class which inherits APIResponseView
	 *
	 * Method will invoke the REST API. Once it receives the response, it will
	 * check if the response returned is not in 2XX series. If not, then it will
	 * throw appropriate exception based on the HTTP Status returned in the
	 * response.
	 *
	 * @param url
	 *            - Complete URL to be invoked. If there are path parameters
	 *            involved, then provide the URL with path parameters already
	 *            placed in URI.
	 * @param httpMethod
	 *            - HTTP Method to be invoked.
	 * @param responseClass
	 *            - Class in which the Response Body should be type casted into.
	 * @param queryParams
	 *            - Query parameters to be sent in the REST API request.
	 * @param headerParams
	 *            - Header parameters to be sent in the REST API request.
	 * @param requestBody
	 *            - Request Body for the REST API. Applicable to HTTP methods
	 *            POST/PUT. If httpMethod is GET/DELETE, provide request body as
	 *            null.
	 * @param acceptableMediaTypes
	 *            - Media or MIME type of response/request
	 * @param rest
	 *            - Rest template going to be used to make the REST API call.
	 * @return ResponseEntity<T> - Response Entity object returned based on the
	 *         REST API call.
	 * @throws com.infosys.fs.exception.BadRequestException 
	 * @throws BadRequestException
	 *             This will be thrown when the REST API returns HTTP 400
	 *             response.
	 * @throws NotFoundException
	 *             This will be thrown when the REST API returns HTTP 404
	 *             response.
	 * @throws InternalServerErrorException
	 *             This will be thrown when the REST API returns HTTP 500
	 *             response.
	 * @throws NotImplementedException
	 *             This will be thrown when the REST API returns HTTP 501
	 *             response.
	 * @throws ExternalSystemException
	 *             This will be thrown when the REST API returns HTTP 550
	 *             response.
	 * @throws ExternalSystemConnectionException
	 *             This will be thrown when the REST API returns HTTP 551
	 *             response.
	 * @throws ExternalSystemUnavailableException
	 *             This will be thrown when the REST API returns HTTP 552
	 *             response.
	 */
	public static <T> ResponseEntity<T> invokeRestAPI(String url, HttpMethod httpMethod, Class<T> responseClass,
			MultiValueMap<String, String> queryParams, Map<String, String> headerParams, Object requestBody,
			List<MediaType> acceptableMediaTypes, RestTemplate rest) throws com.infosys.fs.exception.BadRequestException, NotFoundException, InternalServerErrorException, ExternalSystemUnavailableException {
		String httpMethodName = httpMethod.name();
		ResponseEntity<T> response = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		headerParams.forEach((k, v) -> headers.add(k, v));
		HttpEntity<?> requestEntity = requestBody != null ? new HttpEntity<>(requestBody, headers)
				: new HttpEntity<>(headers);
		long startDate = System.currentTimeMillis();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParams(queryParams);
		response = rest.exchange(builder.build().encode().toUri(), httpMethod, requestEntity, responseClass);
		if (null != response) {
			int statusCode = response.getStatusCodeValue();
			LOGGER.info("Time taken to invoke backend system [{}] Method with URL [{}] and status code [{}] is :[{}]",
					httpMethodName, url, statusCode, System.currentTimeMillis() - startDate);
			// condition to check if the response status returned is in series
			// of 2XX i.e.
			// success response.
			// If the response is not success one, then invoke
			// handleErrorResponse to handle
			// error response.
			if ((statusCode / 100) != 2) {
				APIResponseView view = (APIResponseView) response.getBody();
				ErrorHandlerUtil.handleErrorResponse(statusCode, view.getStatusCode(), view.getStatusDescription(),
						view.getSeverity());
			}
		}
		return response;
	}
}