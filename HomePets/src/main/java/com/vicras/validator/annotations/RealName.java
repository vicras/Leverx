package com.vicras.validator.annotations;

import com.vicras.validator.NameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author viktar hraskou
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = NameValidator.class)
@Documented
public @interface RealName {
    String message() default "Name should consist only latin letters, digits and \"-' \"";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
