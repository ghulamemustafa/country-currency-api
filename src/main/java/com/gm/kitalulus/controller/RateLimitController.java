/**
 * 
 */
package com.gm.kitalulus.controller;

import java.time.Duration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.github.bucket4j.local.LocalBucket;

/**
 * @author Ghulam Mustafa
 *
 */

public class RateLimitController {
	
	private LocalBucket bucket;
	
	@Value("${app.countryinfo.api.ratelimit.perminute}")
	private Integer threashold;
	
	@PostConstruct
	public void postContruct() {
		Bandwidth limit = Bandwidth.classic(threashold, Refill.greedy(threashold, Duration.ofMinutes(1)));
		this.bucket = Bucket4j.builder()
				.addLimit(limit)
				.build();
	}
	
	
	protected boolean hasLimit() {
		return bucket.tryConsume(1);
	}
}
