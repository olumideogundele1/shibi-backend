package com.shibi.app.errorHandler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by User on 08/06/2018.
 */
@Documented
@Constraint(validatedBy = NotNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNumberConstraint {
    String message() default "Invalid data, cannot contain any digit";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
