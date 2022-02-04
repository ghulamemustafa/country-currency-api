/**
 * 
 */
package com.gm.kitalulus.controller;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gm.kitalulus.service.CountryInfoService;

/**
 * @author Ghulam Mustafa
 *
 */
@RestController
@Validated
@RequestMapping("/country")
public class CountryInfoController extends RateLimitController {

	@Autowired
	private CountryInfoService countryAndCurrencyInfoService;
	
	@GetMapping("/{countryname}")
	public ResponseEntity<?> getCountryInfo(@PathVariable("countryname") @NotBlank @Size(min = 3, max = 100) String countryName) {
	    if (hasLimit()) {
	    	return ResponseEntity.ok(countryAndCurrencyInfoService.getCountyAndCurrencyInfo(countryName));
	    }
	    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
	}
}
