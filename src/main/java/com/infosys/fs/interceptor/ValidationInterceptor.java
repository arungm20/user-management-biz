package com.infosys.fs.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosys.fs.exception.APIException.Severity;
import com.infosys.fs.exception.BadRequestException;
import com.infosys.fs.utils.StatusCodes;

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
		
		if (request.getRequestURL().toString().contains("index")) {
			return true;
		}
		
		try (Scope scope = tracer.buildSpan("user-management-biz").startActive(true)) {
			
			if (request.getHeader("MWMD-requestID") != null) {
				scope.span().setBaggageItem("MWMD-requestID", request.getHeader("MWMD-requestID"));
			}
		}
		
		if (handler instanceof HandlerMethod) {
			LOGGER.info("handler instanceof HandlerMethod request {}", request);
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			
			if (handlerMethod.getMethod() != null) {
				Method method = handlerMethod.getMethod();
				
				for (Parameter parameter : method.getParameters()) {
					Map<String, Object> parameterDetails = retrieveParameterAttributes(parameter, request);
					mandatoryParameterCheck(parameterDetails);
				}
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
	
	private void mandatoryParameterCheck(Map<String, Object> parameterDetails) throws BadRequestException {
		
		String parameterName = (String) parameterDetails.get(Constants.NAME);
		
		if ((parameterDetails.get(Constants.REQUIRED) != null) && (Boolean) parameterDetails.get(Constants.REQUIRED)) {
			
			Object parameterValue = parameterDetails.get(Constants.VALUE);
			
			if (this.isNullOrEmpty(parameterValue)) {
				
				throw new BadRequestException(StatusCodes.MISSING_MANDATORY_PARAMETER.getStatusCode(),
						StatusCodes.MISSING_MANDATORY_PARAMETER.getStatusDescription(), Severity.ERROR, parameterName);
			}
		}
	}
	
	private Map<String, Object> retrieveParameterAttributes(Parameter parameter, HttpServletRequest request) {
		
		Map<String, Object> parameterDetails = new HashMap<>();
		Annotation[] annotations = parameter.getAnnotations();
		
		for (Annotation ann : annotations) {
			
			if (ann.annotationType().equals(RequestHeader.class)) {
				
				RequestHeader dv = (RequestHeader) ann;
				parameterDetails.put(Constants.REQUIRED, dv.required());
				parameterDetails.put(Constants.NAME, dv.value());
				parameterDetails.put(Constants.VALUE, request.getHeader(dv.value()));
			}
			if (ann.annotationType().equals(RequestParam.class)) {
				
				RequestParam dv = (RequestParam) ann;
				parameterDetails.put(Constants.REQUIRED, dv.required());
				parameterDetails.put(Constants.NAME, dv.value());
				parameterDetails.put(Constants.VALUE, request.getParameter(dv.value()));
			}
			if (ann.annotationType().equals(PathVariable.class)) {
				PathVariable dv = (PathVariable) ann;
				parameterDetails.put(Constants.REQUIRED, dv.required());
				parameterDetails.put(Constants.NAME, dv.value());
				
				Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
				parameterDetails.put(Constants.VALUE, pathVariables.get(dv.value()));
			}
		}
		return parameterDetails;
	}
	
	private boolean isNullOrEmpty(Object object) {
		
		if (object == null) {
			return true;
		}
		if (object instanceof String) {
			return StringUtils.isBlank((String) object);
		}
		if (object instanceof Collection<?>) {
			return CollectionUtils.isEmpty((Collection<?>) object);
		}
		return false;
	}
	
}
