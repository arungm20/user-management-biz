/*
 * package com.infosys.fs.config;
 * 
 * import org.springframework.boot.web.servlet.ServletRegistrationBean; import
 * org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker; import
 * org.springframework.cloud.netflix.hystrix.EnableHystrix; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration;
 * 
 * import
 * com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
 * 
 *//**
	 * Hystrix Configuration Class
	 *
	 */
/*
 * @EnableHystrix
 * 
 * @EnableCircuitBreaker
 * 
 * @Configuration public class HystrixConfig {
 * 
 *//**
	 * This method will set servlet for hystrix Stream path
	 * 
	 * @return {@link ServletRegistrationBean}
	 *//*
		 * @Bean(name = "hystrixRegistrationBean") public
		 * ServletRegistrationBean servletRegistrationBean() {
		 * ServletRegistrationBean registration = new ServletRegistrationBean(
		 * new HystrixMetricsStreamServlet(), "/hystrix.stream");
		 * registration.setName("hystrixServlet");
		 * registration.setLoadOnStartup(1); return registration; } }
		 */