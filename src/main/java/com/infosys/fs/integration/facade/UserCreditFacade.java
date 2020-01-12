package com.infosys.fs.integration.facade;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infosys.fs.config.APIContext;
import com.infosys.fs.config.CustomRestTemplate;
import com.infosys.fs.exception.BadRequestException;
import com.infosys.fs.exception.ExternalSystemUnavailableException;
import com.infosys.fs.exception.InternalServerErrorException;
import com.infosys.fs.exception.NotFoundException;
import com.infosys.fs.model.APIResponseView;
import com.infosys.fs.model.UserCreditRequest;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserCreditFacade {
	
	@Value("${user.credit.check.url}")
	private String userCreditCheckUrl;
	
	@Autowired
	RestTemplate restTemplate;
	
	//@Async
	@HystrixCommand(commandKey = "credit-check-sys", threadPoolKey = "credit-check-sys")
	public void invoke(APIContext apiContext, UserCreditRequest userCreditRequest) throws BadRequestException, NotFoundException, InternalServerErrorException, ExternalSystemUnavailableException {
		
		CustomRestTemplate.invokeRestAPI(userCreditCheckUrl, HttpMethod.POST, APIResponseView.class, null,
				apiContext.getHeaderParams(), userCreditRequest, Arrays.asList(MediaType.APPLICATION_JSON),
				restTemplate);
	}
	
}
