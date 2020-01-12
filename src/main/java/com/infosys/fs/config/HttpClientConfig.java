package com.infosys.fs.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class which is used to define
 * the connection pool details i.e. for example
 * maximum number of connections to be present in
 * the connection pool, default maximum number of connections
 * to be allocated per route and the maximum number of connections
 * defined against specific routes
 *
 */
@Configuration
@ConfigurationProperties(prefix = "http.conn.pool")
public class HttpClientConfig {

	private Integer maxTotal;
	private Integer defaultMaxPerRoute;
	private List<HttpHostConfiguration> maxPerRoutes;

	/**
	 * Returns the maximum number of total connections
	 * configured to be present in the connection pool.
	 * 
	 * @return Integer
	 */
	public Integer getMaxTotal() {
		return this.maxTotal;
	}

	/**
	 * @param maxTotal - the maxTotal to set
	 */
	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}

	/**
	 * Returns the maximum number of connections that
	 * will be allocated to a given route by default
	 * 
	 * @return Integer
	 */
	public Integer getDefaultMaxPerRoute() {
		return this.defaultMaxPerRoute;
	}

	/**
	 * @param defaultMaxPerRoute - the defaultMaxPerRoute to set
	 */
	public void setDefaultMaxPerRoute(Integer defaultMaxPerRoute) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}

	/**
	 * Returns the list of http host configuration which defines
	 * the maximum number of connections to be allocated in the 
	 * connection pool for given route.
	 * This is custom configuration that can be done for specific
	 * routes if the default route configuration do not suffice.
	 * 
	 * @return List<HttpHostConfiguration>
	 */
	public List<HttpHostConfiguration> getMaxPerRoutes() {
		return this.maxPerRoutes;
	}

	/**
	 * @param maxPerRoutes - the maxPerRoutes to set
	 */
	public void setMaxPerRoutes(List<HttpHostConfiguration> maxPerRoutes) {
		this.maxPerRoutes = maxPerRoutes;
	}

	/**
	 * Configuration class which will be used to define the 
	 * maximum number of connections for a given endpoint (
	 * combination of scheme, host and port) to be allocated
	 * in the connection pool.
	 * 
	 * This will only be used to define custom connection pooling
	 * when the default connection pooling cannot be applied
	 * for a given route.
	 *
	 */
	public static class HttpHostConfiguration {

		private String scheme;
		private String host;
		private Integer port;
		private Integer maxPerRoute;

		/**
		 * Schema of the given route/endpoint i.e.
		 * http or https. To be provided in lower case.
		 * 
		 * @return String
		 */
		public String getScheme() {
			return this.scheme;
		}

		/**
		 * @param scheme - the scheme to set
		 */
		public void setScheme(String scheme) {
			this.scheme = scheme;
		}

		/**
		 * Host name of the route/endpoint for which
		 * configuration is being done.
		 * 
		 * @return String
		 */
		public String getHost() {
			return this.host;
		}

		/**
		 * @param host - the host to set
		 */
		public void setHost(String host) {
			this.host = host;
		}

		/**
		 * Port of the given route/endpoint
		 * 
		 * @return Integer
		 */
		public Integer getPort() {
			return this.port;
		}

		/**
		 * @param port - the port to set
		 */
		public void setPort(Integer port) {
			this.port = port;
		}

		/**
		 * Maximum number of connections to be allocated
		 * per the given route
		 * 
		 * @return Integer
		 */
		public Integer getMaxPerRoute() {
			return maxPerRoute;
		}

		/**
		 * @param maxPerRoute - the maxPerRoute to set
		 */
		public void setMaxPerRoute(Integer maxPerRoute) {
			this.maxPerRoute = maxPerRoute;
		}
	}
}