package com.infosys.fs.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * Setter for hystrix properties
 *
 */
public class HystrixPropertySetter extends HystrixCommand<String> {

	/**
	 * This method will load Hystrix command key and group key
	 * @param key Hystrix Command Key
	 * @param group Hystrix Group Key
	 */
	public HystrixPropertySetter(String key, String group) {

		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(group))
				.andCommandKey(HystrixCommandKey.Factory.asKey(key))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(key)));
	}

	@Override
	protected String run() throws Exception {
		return null;
	}
}