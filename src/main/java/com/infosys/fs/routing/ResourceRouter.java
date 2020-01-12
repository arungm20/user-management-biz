
package com.infosys.fs.routing;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.infosys.fs.config.APIContext;
import com.infosys.fs.exception.BadRequestException;
import com.infosys.fs.exception.ExternalSystemUnavailableException;
import com.infosys.fs.exception.InternalServerErrorException;
import com.infosys.fs.model.UserCreditRequest;
import com.infosys.fs.model.UserCreditResponse;
import com.infosys.fs.service.handler.UserCreditHandler;

@Service
public class ResourceRouter {
	
	@Autowired
	private UserCreditHandler userCreditHandler;
	
	public ResponseEntity<Object> process(APIContext apiContext, UserCreditRequest userCreditRequest)
			throws ExternalSystemUnavailableException, InternalServerErrorException, BadRequestException {
		
		return userCreditHandler.checkCredit(apiContext, userCreditRequest);
		
	}
	
	public ResponseEntity<Object> processResponse(APIContext apiContext, @Valid UserCreditResponse creditCheckResponse)
			throws ExternalSystemUnavailableException, InternalServerErrorException {
		
		return userCreditHandler.checkCreditResponse(apiContext, creditCheckResponse);
		
	}
	
}
