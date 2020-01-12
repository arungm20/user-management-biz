package com.infosys.fs.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import io.opentracing.Scope;
import io.opentracing.Tracer;

/**
 * The Intercepter class which will have all validation logic for mandatory
 * request parameter
 *
 */
@Component
public class ValidationInterceptor implements HandlerInterceptor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationInterceptor.class);
	
	@Autowired
	private Tracer tracer;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		/*
		 * LOGGER.info("In ValidationInterceptor"); Span span =
		 * tracer.activeSpan();
		 * 
		 * LOGGER.info("In ValidationInterceptor span: {}", span); if(null ==
		 * span.getBaggageItem("MWMD-requestID".toLowerCase())) {
		 * span.setBaggageItem("MWMD-requestID".toLowerCase(),
		 * request.getHeader("MWMD-requestID")); }
		 */
		
		if(request.getRequestURL().toString().contains("index")) {
			return true;
		}
		
		try (Scope scope = tracer.buildSpan("user-management-biz").startActive(true)) {
			
			if (request.getHeader("MWMD-requestID") != null) {
				scope.span().setBaggageItem("MWMD-requestID", request.getHeader("MWMD-requestID"));
			}
		}
		

		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// over riding parent method
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// over riding parent method
	}
	
}
