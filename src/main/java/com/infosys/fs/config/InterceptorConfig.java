package com.infosys.fs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.infosys.fs.interceptor.ValidationInterceptor;

/**
 * This class is used to inject customized intercepter class
 *
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer  {

	@Autowired
	ValidationInterceptor validationInterceptor;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(validationInterceptor);
	}
	
}
