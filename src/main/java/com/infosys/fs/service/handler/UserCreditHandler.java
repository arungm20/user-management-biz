package com.infosys.fs.service.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.infosys.fs.config.APIContext;
import com.infosys.fs.exception.BadRequestException;
import com.infosys.fs.exception.ExternalSystemUnavailableException;
import com.infosys.fs.exception.InternalServerErrorException;
import com.infosys.fs.exception.APIException.Severity;
import com.infosys.fs.integration.NotificationService;
import com.infosys.fs.integration.UserCreditService;
import com.infosys.fs.model.APIResponseView;
import com.infosys.fs.model.UserCreditRequest;
import com.infosys.fs.model.UserCreditResponse;
import com.infosys.fs.utils.StatusCodes;

@Service
public class UserCreditHandler {
	
	@Autowired
	private UserCreditService userCreditService;
	
	@Autowired
	private NotificationService notificationService;
	
	private Map<String, String> creditCheckMap = new HashMap<>();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserCreditHandler.class);
	
	public ResponseEntity<Object> checkCredit(APIContext apiContext, UserCreditRequest userCreditRequest)
			throws ExternalSystemUnavailableException, InternalServerErrorException, BadRequestException {
		validateCreditRequest(userCreditRequest);
		
		if (creditCheckMap.get(userCreditRequest.getId()) != null) {
			APIResponseView apiResponseView = new APIResponseView();
			apiResponseView.setStatusCode("409");
			apiResponseView.setStatusDescription("ALREADY QUEUED");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponseView);
		}
		
		userCreditService.processCreditCheckRequest(apiContext, userCreditRequest);
		creditCheckMap.put(userCreditRequest.getId(), "PROCESSING");
		APIResponseView apiResponseView = new APIResponseView();
		apiResponseView.setStatusCode("202");
		apiResponseView.setStatusDescription("ACCEPTED");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponseView);
	}
	
	private void validateCreditRequest(UserCreditRequest userCreditRequest) throws BadRequestException {
		
		if (StringUtils.isBlank(userCreditRequest.getId())) {
			throw new BadRequestException(StatusCodes.MISSING_MANDATORY_PARAMETER.getStatusCode(),
					StatusCodes.MISSING_MANDATORY_PARAMETER.getStatusDescription(), Severity.ERROR, "id");
			
		}
		
		if (StringUtils.isBlank(userCreditRequest.getName())) {
			throw new BadRequestException(StatusCodes.MISSING_MANDATORY_PARAMETER.getStatusCode(),
					StatusCodes.MISSING_MANDATORY_PARAMETER.getStatusDescription(), Severity.ERROR, "name");
			
		}
	}
	
	public ResponseEntity<Object> checkCreditResponse(APIContext apiContext, UserCreditResponse creditCheckResponse) {
		
		if (creditCheckMap.get(creditCheckResponse.getId()) != null) {
			
			creditCheckMap.remove(creditCheckResponse.getId());
			LOGGER.info("Received Response for id : {} with credit Score : {}", creditCheckResponse.getId(),
					creditCheckResponse.getScore());
			notificationService.send(creditCheckResponse.getId() + " | " + creditCheckResponse.getName() + " | "
					+ evaluateCreditScore(creditCheckResponse.getScore()));
			APIResponseView apiResponseView = new APIResponseView();
			apiResponseView.setStatusCode("200");
			apiResponseView.setStatusDescription("OK");
			return ResponseEntity.status(HttpStatus.OK).body(apiResponseView);
			// Process Further business Logic
			
		}
		APIResponseView apiResponseView = new APIResponseView();
		apiResponseView.setStatusCode("404");
		apiResponseView.setStatusDescription("NOT_FOUND");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponseView);
		
	}
	
	private String evaluateCreditScore(String creditScore) {
		
		if (creditScore.equals("NOT_FOUND")) {
			return creditScore;
		}
		
		int score = Integer.parseInt(creditScore);
		
		if (score < 579) {
			return creditScore + " | VERY POOR";
		} else if (score < 669) {
			return creditScore + " | FAIR";
		} else if (score < 739) {
			return creditScore + " | GOOD";
		} else if (score < 799) {
			return creditScore + " | VERY GOOD";
		} else {
			return creditScore + " | EXCELLENT";
		}
	}
	
}
