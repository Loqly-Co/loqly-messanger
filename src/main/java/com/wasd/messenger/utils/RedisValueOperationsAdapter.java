package com.wasd.messenger.utils;

import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public record RedisValueOperationsAdapter(RedisTemplate<String, Object> redisTemplate) {

	public <T> T get(Object key, Class<T> forClass) {
		return forClass.cast(redisTemplate.opsForValue().get(key));
	}

	public <T> T getAndDelete(String key, Class<T> forClass) {
		return forClass.cast(redisTemplate.opsForValue().getAndDelete(key));
	}

	public <T> T getAndExpire(String key, long timeout, TimeUnit unit, Class<T> forClass) {
		return forClass.cast(redisTemplate.opsForValue().getAndExpire(key, timeout, unit));
	}

	public <T> T getAndExpire(String key, Duration timeout, Class<T> forClass) {
		return forClass.cast(redisTemplate.opsForValue().getAndExpire(key, timeout));
	}

	public <T> T getAndPersist(String key, Class<T> forClass) {
		return forClass.cast(redisTemplate.opsForValue().getAndPersist(key));
	}

	public <T> T getAndSet(String key, Object value, Class<T> forClass) {
		return forClass.cast(redisTemplate.opsForValue().getAndSet(key, value));
	}

	public <T> List<T> multiGet(Collection<String> keys, Class<T> forClass) {
		return (List<T>) redisTemplate.opsForValue().multiGet(keys);
	}

	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	public void set(String key, Object value, long timeout, TimeUnit unit) {
		redisTemplate.opsForValue().set(key, value, timeout, unit);
	}

	public Boolean setIfAbsent(String key, Object value) {
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	public Boolean setIfAbsent(String key, Object value, long timeout, TimeUnit unit) {
		return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
	}

	public Boolean setIfPresent(String key, Object value) {
		return redisTemplate.opsForValue().setIfPresent(key, value);
	}

	public Boolean setIfPresent(String key, Object value, long timeout, TimeUnit unit) {
		return redisTemplate.opsForValue().setIfPresent(key, value, timeout, unit);
	}

	public void multiSet(Map<? extends String, ?> map) {
		redisTemplate.opsForValue().multiSet(map);
	}

	public Boolean multiSetIfAbsent(Map<? extends String, ?> map) {
		return redisTemplate.opsForValue().multiSetIfAbsent(map);
	}

	public Long increment(String key) {
		return redisTemplate.opsForValue().increment(key);
	}

	public Integer append(String key, String value) {
		return redisTemplate.opsForValue().append(key, value);
	}

	public void set(String key, Object value, long offset) {
		redisTemplate.opsForValue().set(key, value, offset);
	}

	public Long size(String key) {
		return redisTemplate.opsForValue().size(key);
	}

	public Long decrement(String key, long delta) {
		return redisTemplate.opsForValue().decrement(key, delta);
	}

	public Double increment(String key, double delta) {
		return redisTemplate.opsForValue().increment(key, delta);
	}

	public Long increment(String key, long delta) {
		return redisTemplate.opsForValue().increment(key, delta);
	}

	public Long decrement(String key) {
		return redisTemplate.opsForValue().decrement(key);
	}

	public Boolean setBit(String key, long offset, boolean value) {
		return redisTemplate.opsForValue().setBit(key, offset, value);
	}

	public Boolean getBit(String key, long offset) {
		return redisTemplate.opsForValue().getBit(key, offset);
	}

	public List<Long> bitField(String key, BitFieldSubCommands subCommands) {
		return redisTemplate.opsForValue().bitField(key, subCommands);
	}

	public RedisOperations<String, Object> getOperations() {
		return redisTemplate;
	}

	public void set(String key, Object value, Duration timeout) {
		redisTemplate.opsForValue().set(key, value, timeout);
	}

	public Boolean setIfAbsent(String key, Object value, Duration timeout) {
		return redisTemplate.opsForValue().setIfAbsent(key, value, timeout);
	}

	public Boolean setIfPresent(String key, Object value, Duration timeout) {
		return redisTemplate.opsForValue().setIfPresent(key, value, timeout);
	}

	public boolean exists(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}
}
