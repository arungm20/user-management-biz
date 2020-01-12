package com.infosys.fs.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosys.fs.exception.APIException.Severity;
import com.infosys.fs.exception.BadRequestException;
import com.infosys.fs.exception.ExternalSystemUnavailableException;
import com.infosys.fs.exception.InternalServerErrorException;
import com.infosys.fs.exception.NotFoundException;

public class ErrorHandlerUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandlerUtil.class);
	
	public static void handleErrorResponse(int httpStatus, String statusCode, String statusDescription, String severity)
			throws BadRequestException, NotFoundException, InternalServerErrorException,
			ExternalSystemUnavailableException {
		switch (httpStatus) {
			case 400:
				throw new BadRequestException(statusCode, statusDescription, mapSeverity(severity));
			case 404:
				throw new NotFoundException(statusCode, statusDescription, mapSeverity(severity));
			case 500:
				throw new InternalServerErrorException(statusCode, statusDescription, mapSeverity(severity));
				
			default:
				throw new ExternalSystemUnavailableException(statusCode, statusDescription, mapSeverity(severity));
		}
	}
	
	private static Severity mapSeverity(String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		}
		
		Severity severity = null;
		try {
			severity = Severity.valueOf(value);
		} catch (IllegalArgumentException e) {
			LOGGER.error("IllegalArgumentException for value: [{}]", value, e);
		}
		
		return severity;
	}
	
}
