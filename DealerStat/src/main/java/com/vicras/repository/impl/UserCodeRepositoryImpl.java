package com.vicras.repository.impl;

import com.vicras.repository.UserCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class UserCodeRepositoryImpl implements UserCodeRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String, Object> valueOperations;

    @Autowired
    public UserCodeRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void save(String code, Long userId) {
        valueOperations.set(code, userId);
    }

    @Override
    public void saveTemporarily(String code, Long userId, long timeout, TimeUnit timeUnit) {
        save(code, userId);
        redisTemplate.expire(code, timeout, timeUnit);
    }

    @Override
    public Optional<Long> findWithCode(String code) {
        Object value = valueOperations.get(code);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(Long.parseLong((String) value));
    }

    @Override
    public boolean isExistWithCode(String code) {
        return findWithCode(code).isPresent();
    }

    @Override
    public void update(String code, Long userId) {
        valueOperations.setIfPresent(code, userId);
    }

    @Override
    public void deleteWithCode(String code) {
        redisTemplate.delete(code);
    }
}
