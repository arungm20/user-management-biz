
package com.infosys.fs.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Initialize hystrix properties at the startup
 * 
 *
 */

@Configuration
public class HystrixPropertyInitializer {
	
	@Value("${hystrix.group.key}")
	private String groupKey;
	
	@Value("#{'${hystrix.key.name.list}'.split(',')}")
	private List<String> hystrixKeyList;
	
	/**
	 * It will initialize Hystrix property during application start up
	 */
	@PostConstruct
	public void intializeProperties() {
		for (String key : hystrixKeyList) {
			new HystrixPropertySetter(key, groupKey);
		}
	}
}
