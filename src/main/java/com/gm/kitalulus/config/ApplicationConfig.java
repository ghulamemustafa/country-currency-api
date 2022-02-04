package com.gm.kitalulus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.gm.kitalulus.interceptor.IntegrationClientHttpInterceptor;

@Configuration
@EnableCaching
public class ApplicationConfig {

	@Value("${app.integration.countryinfo.api.url}")
	private String countryInfoApiUrl;
	@Value("${app.integration.currencyinfo.api.url}")
	private String currencyApiUrl;
	@Value("${app.integration.countryinfo.api.logging:true}")
	private Boolean countryInfoApiLogging;
	@Value("${app.integration.currencyinfo.api.logging:true}")
	private Boolean currencyInfoApiLogging;

	
	@Bean
	public RestTemplate countryInfoRestTemplate(RestTemplateBuilder builder) {
		builder = builder.rootUri(countryInfoApiUrl);
		if(Boolean.TRUE.equals(countryInfoApiLogging)) {
			builder = builder.interceptors(new IntegrationClientHttpInterceptor());
		}
		return builder.build();
	}
	
	@Bean
	public RestTemplate currencyInfoRestTemplate(RestTemplateBuilder builder) {
		builder = builder.rootUri(currencyApiUrl);
		if(Boolean.TRUE.equals(countryInfoApiLogging)) {
			builder = builder.interceptors(new IntegrationClientHttpInterceptor());
		}
		return builder.build();	}
}
