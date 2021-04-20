package com.vicras.service;

import com.vicras.entity.User;

public interface CodeGeneratorService {
    String generateUniqueCode(User user);
}
