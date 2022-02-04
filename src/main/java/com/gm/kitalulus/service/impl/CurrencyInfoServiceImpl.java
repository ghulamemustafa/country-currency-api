/**
 * 
 */
package com.gm.kitalulus.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gm.kitalulus.service.CurrencyInfoService;

/**
 * @author Ghulam Mustafa
 *
 */
@Service
public class CurrencyInfoServiceImpl implements CurrencyInfoService {

	private static final ParameterizedTypeReference<HashMap<String, Double>> RESPONSE_TYPE = new ParameterizedTypeReference<HashMap<String, Double>>() {};

	@Value("${app.integration.currencyinfo.api.key}")
	private String currencyApiKey;
	
	@Autowired
	private RestTemplate currencyInfoRestTemplate;

	
	@Override
	@Cacheable(value = "conversionCache", key = "#from+'-'+#to")
	public Double getConversionRate(String from, String to) {
		String pairKey = from+"_"+to;
		ResponseEntity<HashMap<String, Double>> responseEntity = currencyInfoRestTemplate.exchange("/convert?compact=ultra&apiKey="+currencyApiKey+"&q="+pairKey, HttpMethod.GET, null, RESPONSE_TYPE);
		HashMap<String, Double> conversionMap = responseEntity.getBody();
		return conversionMap.get(pairKey);
	}

}
