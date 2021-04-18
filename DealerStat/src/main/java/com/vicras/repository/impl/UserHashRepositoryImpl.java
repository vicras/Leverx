package com.vicras.repository.impl;

import com.vicras.entity.UserHash;
import com.vicras.repository.UserHashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserHashRepositoryImpl implements UserHashRepository {

    private static final String KEY = "UserHash";

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, UserHash> hashOperations;

    @Autowired
    public UserHashRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(UserHash userHash) {
        hashOperations.put(KEY, userHash.getHash(), userHash);
    }

    @Override
    public Optional<UserHash> findWithHash(String hash) {
        return Optional.ofNullable(hashOperations.get(KEY, hash));
    }

    @Override
    public Map<String, UserHash> findAll() {
        return hashOperations.entries(KEY);
    }

    @Override
    public boolean isExistWithHash(String hash) {
        return hashOperations.hasKey(KEY, hash);
    }

    @Override
    public void update(UserHash userHash) {
        hashOperations.put(KEY, userHash.getHash(), userHash);
    }

    @Override
    public void deleteWithHash(String hash) {
        hashOperations.delete(KEY, hash);
    }
}
