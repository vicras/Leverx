package com.vicras.repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface UserCodeRepository {

    void save(String code, Long userId);

    void saveTemporarily(String code, Long userId, long timeout, TimeUnit timeUnit);

    Optional<Long> findWithCode(String code);

    boolean isExistWithCode(String code);

    void update(String code, Long userId);

    void deleteWithCode(String code);
}