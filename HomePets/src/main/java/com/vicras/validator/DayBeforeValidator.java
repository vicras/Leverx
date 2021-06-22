package com.vicras.validator;

import com.vicras.validator.annotations.DayBeforeNow;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.apache.logging.log4j.util.Strings.EMPTY;

/**
 * @author viktar hraskou
 */
@Component
public class DayBeforeValidator implements ConstraintValidator<DayBeforeNow, LocalDate> {

    private LocalDate maximalAllowedDate;

    @Override
    public void initialize(DayBeforeNow annotation) {
        String valueDate = annotation.value();
        maximalAllowedDate = EMPTY.equals(valueDate) ? LocalDate.now() : LocalDate.parse(valueDate, ISO_LOCAL_DATE);
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        return date != null && date.isBefore(maximalAllowedDate);
    }

}