/**
 * 
 */
package com.gm.kitalulus.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.gm.kitalulus.dto.CountryInfo;
import com.gm.kitalulus.dto.Currency;
import com.gm.kitalulus.mapper.CountryInfoMapper;
import com.gm.kitalulus.service.CountryInfoService;
import com.gm.kitalulus.service.CurrencyInfoService;

/**
 * @author Ghulam Mustafa
 *
 */
@Service
public class CountryInfoServiceImpl implements CountryInfoService {

	private static final ParameterizedTypeReference<List<JsonNode>> RESPONSE_TYPE = new ParameterizedTypeReference<List<JsonNode>>() {};

	@Autowired
	private RestTemplate countryInfoRestTemplate;
	
	@Autowired
	private CountryInfoMapper countryInfoMapper;

	@Autowired
	private CurrencyInfoService currecnyInfoService;
	
	@Value("${app.base.currency}")
	private String baseCurrency;

	@Override
	public List<CountryInfo> getCountyAndCurrencyInfo(String countryName) {
		ResponseEntity<List<JsonNode>> responseEntity = countryInfoRestTemplate.exchange("/name/"+countryName, HttpMethod.GET, null, RESPONSE_TYPE);
		List<CountryInfo> countryInfos = countryInfoMapper.map(responseEntity.getBody());
		countryInfos.parallelStream().forEach(countryInfo -> {
			List<Currency> currencies = countryInfo.getCurrencies().stream().map(currency->{
				Double rate =  currecnyInfoService.getConversionRate(currency.getCode(), baseCurrency);
				currency.setExchangeRate(rate);
				return currency;
			}).collect(Collectors.toList());
			countryInfo.setCurrencies(currencies);
		});
		return countryInfos;
	}

}
