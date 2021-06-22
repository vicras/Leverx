package com.vicras.validator;

import com.vicras.validator.annotations.RealName;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author viktar hraskou
 */
@Component
public class NameValidator implements ConstraintValidator<RealName, String> {

    private static final String NAME_PATTERN = "[A-Za-z- '0-9]+";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(NAME_PATTERN);
    }

}
