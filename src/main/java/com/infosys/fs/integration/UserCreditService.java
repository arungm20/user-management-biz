package com.infosys.fs.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.fs.config.APIContext;
import com.infosys.fs.exception.APIException.Severity;
import com.infosys.fs.integration.facade.UserCreditFacade;
import com.infosys.fs.exception.BadRequestException;
import com.infosys.fs.exception.ExternalSystemUnavailableException;
import com.infosys.fs.exception.InternalServerErrorException;
import com.infosys.fs.exception.NotFoundException;
import com.infosys.fs.model.UserCreditRequest;
import com.infosys.fs.utils.StatusCodes;
import com.netflix.hystrix.exception.HystrixRuntimeException;

@Service
public class UserCreditService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserCreditService.class);
	
	@Autowired
	private UserCreditFacade userCreditFacade;
	
	public void processCreditCheckRequest(APIContext apiContext, UserCreditRequest userCreditRequest)
			throws ExternalSystemUnavailableException, InternalServerErrorException {
		
		try {
			LOGGER.info("Invoking Credit Check System Layer for : {}", userCreditRequest.getId());
			userCreditFacade.invoke(apiContext, userCreditRequest);
		} catch (BadRequestException | NotFoundException | InternalServerErrorException e) {
			LOGGER.error("Exception : {}", e);
			throw new InternalServerErrorException(StatusCodes.INTERNAL_SERVER_ERROR.getStatusCode(),
					StatusCodes.INTERNAL_SERVER_ERROR.getStatusDescription(), Severity.CRITICAL);
		} catch (HystrixRuntimeException he) {
			LOGGER.error("HystrixRuntimeException : {}", he);
			throw he;
		}
	}
	
}
