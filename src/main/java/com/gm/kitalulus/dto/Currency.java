package com.gm.kitalulus.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@RequiredArgsConstructor
@ToString
public class Currency {
	@NonNull
	private String code;
	private Double exchangeRate;
}
