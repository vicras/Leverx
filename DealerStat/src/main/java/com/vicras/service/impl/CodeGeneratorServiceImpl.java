package com.vicras.service.impl;

import com.vicras.entity.User;
import com.vicras.service.CodeGeneratorService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

    @Override
    public String generateUniqueCode(User user) {
        String time = LocalDateTime.now().toString();
        var line = user.getEmail() + time + user.getId();
        int hashCode = line.hashCode();
        return UUID.randomUUID().toString() + hashCode;
    }
}
