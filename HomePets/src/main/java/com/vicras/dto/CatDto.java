package com.vicras.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vicras.model.enums.CatBreed;
import com.vicras.validator.annotations.RealName;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author viktar hraskou
 */
@Data
public class CatDto {

    @Range
    @JsonProperty("id")
    private Long id;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @RealName
    @JsonProperty("name")
    private String name;

    @Range
    @NotNull
    @JsonProperty("owner_id")
    private Long owner;

    @JsonProperty("bread")
    private CatBreed breed;

}
