package com.infosys.fs.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTemplate.class);
	
	@Value("${group.id}")
	private String kafkaTopic;
	
	public void send(String message) {
	    
		LOGGER.info("kafkaTopic : {} : Sending Message : {}", kafkaTopic, message);
	    kafkaTemplate.send(kafkaTopic, message);
	}
	
}
