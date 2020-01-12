
package com.infosys.fs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.internal.samplers.ConstSampler;

/**
 * This class is used to trace the request and response coming to this API
 *
 */

@Configuration
public class TracingConfiguration {
	
	/**
	 * This method is used to get the endpoint and make a trace of request and
	 * response
	 *
	 * @return io.opentracing.Tracer
	 */
	@Bean
	public io.opentracing.Tracer jaegerTracer() {
		
		SamplerConfiguration samplerConfiguration = io.jaegertracing.Configuration.SamplerConfiguration.fromEnv()
				.withType(ConstSampler.TYPE).withParam(1);;
		
		ReporterConfiguration reporterConfiguration = io.jaegertracing.Configuration.ReporterConfiguration.fromEnv()
				.withLogSpans(true);
		
		io.jaegertracing.Configuration configuration = new io.jaegertracing.Configuration("user-management-biz");
		
		configuration.withReporter(reporterConfiguration);
		configuration.withSampler(samplerConfiguration);
		return configuration.getTracer();
	}
	
}
