package com.vicras.repository;

import com.vicras.entity.UserHash;

import java.util.Map;
import java.util.Optional;

public interface UserHashRepository {

    void save(UserHash userHash);
    Optional<UserHash> findWithHash(String hash);
    Map<String, UserHash> findAll();
    boolean isExistWithHash(String hash);
    void update(UserHash userHash);
    void deleteWithHash(String hash);
}