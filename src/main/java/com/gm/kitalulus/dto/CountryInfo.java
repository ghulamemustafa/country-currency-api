
package com.gm.kitalulus.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class CountryInfo {
	private String fullName;
	private Integer population;
	@Singular
	private List<Currency> currencies;
}
