/**
 * 
 */
package com.gm.kitalulus.mapper;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.gm.kitalulus.dto.CountryInfo;
import com.gm.kitalulus.dto.CountryInfo.CountryInfoBuilder;
import com.gm.kitalulus.dto.Currency;

/**
 * @author Ghulam Mustafa
 *
 */

@Component
public class CountryInfoMapper {

	@Value("${app.integration.countryinfo.api.fullname.field.path}")
	private String fullNameFieldPath;
	
	public List<CountryInfo> map(List<JsonNode> countryNodes) {
		return countryNodes.stream().map(countryNode->{
			CountryInfoBuilder builder = CountryInfo.builder()
			.fullName(countryNode.at(fullNameFieldPath).asText())
			.population(countryNode.get("population").intValue());
			
			JsonNode currenciesNode = countryNode.get("currencies");
			Iterator<Entry<String, JsonNode>> fields = currenciesNode.fields();
			while(fields.hasNext()) {
				builder.currency(new Currency(fields.next().getKey()));
			}
			return builder.build();
		}).collect(Collectors.toList());
	}
}
