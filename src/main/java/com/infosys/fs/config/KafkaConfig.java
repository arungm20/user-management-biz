
package com.infosys.fs.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration

@EnableKafka
public class KafkaConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty(Constants.KAFKA_BOOTSTRAP_SERVERS));
		props.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty(Constants.KAFKA_GROUP_ID));
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, env.getProperty(Constants.KAFKA_KEY_DESERIALIZER));
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, env.getProperty(Constants.KAFKA_VALUE_DESERIALIZER));
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, env.getProperty(Constants.KAFKA_ENABLE_AUTO_COMMIT));
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,
				env.getProperty(Constants.KAFKA_AUTO_COMMIT_INTERVAL_MS));
		
		return new DefaultKafkaConsumerFactory<>(props);
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
	
}
