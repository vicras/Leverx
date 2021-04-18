package com.vicras.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@org.springframework.data.redis.core.RedisHash("Student")
public class UserHash implements Serializable {

    @Id
    @Indexed
    private String hash;
    private Long userId;
}
