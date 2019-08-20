package com.xs.rongly.framework.stater.redis.autoconfig;

import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.Nullable;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义的redis cache writer的类， 如果连不上redis或者命令 超时 会直接进行后续逻辑
 * Created on 2018/2/1.
 */
@Slf4j
public class CustomRedisCacheWriter implements RedisCacheWriter {

  private RedisCacheWriter delegate;

  public CustomRedisCacheWriter(RedisCacheWriter delegate) {
    this.delegate = delegate;
  }

  @Override
  public void put(String s, byte[] bytes, byte[] bytes1, @Nullable Duration duration) {
    try {
      delegate.put(s, bytes, bytes1, duration);
    } catch (Exception e) {
      handleException(e);
    }
  }

  @Nullable
  @Override
  public byte[] get(String s, byte[] bytes) {
    try {
      return delegate.get(s, bytes);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @Nullable
  @Override
  public byte[] putIfAbsent(String s, byte[] bytes, byte[] bytes1, @Nullable Duration duration) {
    try {
      return delegate.putIfAbsent(s, bytes, bytes1, duration);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @Override
  public void remove(String s, byte[] bytes) {
    try {
      delegate.remove(s, bytes);
    } catch (Exception e) {
      handleException(e);
    }
  }

  @Override
  public void clean(String s, byte[] bytes) {
    try {
      delegate.clean(s, bytes);
    } catch (Exception e) {
      handleException(e);
    }
  }

  private <T> T handleException(Exception e) {
    log.error("handleException", e);
    return null;
  }
}