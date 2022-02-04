/**
 * 
 */
package com.gm.kitalulus.service;

import java.util.List;

import com.gm.kitalulus.dto.CountryInfo;

/**
 * @author Ghulam Mustafa
 *
 */
public interface CountryInfoService {
	public List<CountryInfo> getCountyAndCurrencyInfo(String countryName);
}
