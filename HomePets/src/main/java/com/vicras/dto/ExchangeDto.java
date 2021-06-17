package com.vicras.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author viktar hraskou
 */
@Data
public class ExchangeDto {

    @Range
    @NotNull
    @JsonProperty("owner_from")
    private Long ownerFrom;

    @UniqueElements
    @JsonProperty("animals_from")
    private List<Long> animalsFrom;

    @Range
    @NotNull
    @JsonProperty("owner_to")
    private Long ownerTo;

    @UniqueElements
    @JsonProperty("animals_to")
    private List<Long> animalsTo;

}
