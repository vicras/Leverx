package com.vicras.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vicras.validator.annotations.DayBeforeNow;
import com.vicras.validator.annotations.RealName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author viktar hraskou
 */
@Data
public class PersonDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @RealName
    @JsonProperty("name")
    private String name;

    @DayBeforeNow
    @JsonProperty("birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

}
