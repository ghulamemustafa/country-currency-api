/**
 * 
 */
package com.gm.kitalulus.exception.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ghulam Mustafa
 *
 */
@Setter
@Getter
@Builder
public class ApiError {
	private String message;
	private Integer status;
}
