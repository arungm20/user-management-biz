package com.infosys.fs.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.infosys.fs.service.handler.MessageHandler;

@Configuration
@EnableKafka
public class KafkaListenerConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListenerConfig.class);
	
	@Autowired
	private MessageHandler messageHandler;

	@KafkaListener(topics = "credit-score-topic", groupId="group-credit-score-topic")
	public void listen(String message) {

		LOGGER.info("Received Messasge:  {}", message);
		
		SseEmitter latestEm = messageHandler.getLatestEmitter();
				
		try {
			latestEm.send(message);
		} catch (IOException e) {
			latestEm.completeWithError(e);
		}

	}
}