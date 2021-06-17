package com.vicras.validator;

import com.vicras.dto.ExchangeDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author viktar hraskou
 */
@Component
public class ExchangeRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        var exchangeDto = (ExchangeDto) target;

        Long ownerTo = exchangeDto.getOwnerTo();
        Long ownerFrom = exchangeDto.getOwnerFrom();

        if (ownerFrom.equals(ownerTo)) {
            errors.reject("From and To owner should be different people");
        }
    }

}
