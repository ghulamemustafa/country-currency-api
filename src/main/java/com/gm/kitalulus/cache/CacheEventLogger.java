/**
 * 
 */
package com.gm.kitalulus.cache;


import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ghulam Mustafa
 *
 */
@Slf4j
public class CacheEventLogger implements CacheEventListener<Object, Object> {

  @Override
  public void onEvent(
    CacheEvent<? extends Object, ? extends Object> cacheEvent) {
      log.info("cahce event :{}, key : {}, old-value :{}, new-value : {}",cacheEvent.getType(),cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
  }
}
