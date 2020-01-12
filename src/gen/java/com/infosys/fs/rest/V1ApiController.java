package com.infosys.fs.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.fs.config.APIContext;
import com.infosys.fs.exception.BadRequestException;
import com.infosys.fs.exception.ExternalSystemUnavailableException;
import com.infosys.fs.exception.InternalServerErrorException;
import com.infosys.fs.model.UserCreditRequest;
import com.infosys.fs.model.UserCreditResponse;
import com.infosys.fs.routing.ResourceRouter;

import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-02T10:36:32.149+05:30")

@Controller
public class V1ApiController implements V1Api {

    private static final Logger log = LoggerFactory.getLogger(V1ApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired
    private ResourceRouter router;

    @org.springframework.beans.factory.annotation.Autowired
    public V1ApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Object> userCredit(@ApiParam(value = "Indicates the time at which request was created. Date format: ISO-8601 format (yyyy-MM-dd'T'HH:mm:ss'Z' or yyyy-MM-dd'T'HH:mm:ss±hh:mm)" ,required=true) @RequestHeader(value="MWMD-requestTimestamp", required=true) String mwMDRequestTimestamp,@ApiParam(value = "Identifies the name of the business activity for the message being exchanged. For MYUPC channel, this should be provided as \"CustomerCare\"" ,required=true) @RequestHeader(value="MWMD-activityName", required=true) String mwMDActivityName,@ApiParam(value = "RequestID is a unique identifier that is unique for a single request/response message and will be used to map log and error message to consumer’s request." ,required=true) @RequestHeader(value="MWMD-requestID", required=true) String mwMDRequestID,@ApiParam(value = "Represents  Resource entity" ,required=true )  @Valid @RequestBody UserCreditRequest resource) throws ExternalSystemUnavailableException, InternalServerErrorException, BadRequestException {
        APIContext apiContext = new APIContext();
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("MWMD-requestTimestamp ", mwMDRequestTimestamp);
        headerParams.put("MWMD-activityName", mwMDActivityName);
        headerParams.put("MWMD-requestID", mwMDRequestID);
		apiContext.setHeaderParams(headerParams );
        return router.process(apiContext, resource);
    }

    public ResponseEntity<Object> userCreditResponse(@ApiParam(value = "Indicates the time at which request was created. Date format: ISO-8601 format (yyyy-MM-dd'T'HH:mm:ss'Z' or yyyy-MM-dd'T'HH:mm:ss±hh:mm)" ,required=true) @RequestHeader(value="MWMD-requestTimestamp", required=true) String mwMDRequestTimestamp,@ApiParam(value = "Identifies the name of the business activity for the message being exchanged. For MYUPC channel, this should be provided as \"CustomerCare\"" ,required=true) @RequestHeader(value="MWMD-activityName", required=true) String mwMDActivityName,@ApiParam(value = "RequestID is a unique identifier that is unique for a single request/response message and will be used to map log and error message to consumer’s request." ,required=true) @RequestHeader(value="MWMD-requestID", required=true) String mwMDRequestID,@ApiParam(value = "Represents  Credit Check Response" ,required=true )  @Valid @RequestBody UserCreditResponse creditCheckResponse) throws ExternalSystemUnavailableException, InternalServerErrorException {
        String accept = request.getHeader("Accept");
        APIContext apiContext = new APIContext();
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("MWMD-requestTimestamp ", mwMDRequestTimestamp);
        headerParams.put("MWMD-activityName", mwMDActivityName);
        headerParams.put("MWMD-requestID", mwMDRequestID);
		apiContext.setHeaderParams(headerParams );        
		return router.processResponse(apiContext, creditCheckResponse);
    }

}
