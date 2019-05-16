package com.shibi.app.errorHandler;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by User on 08/06/2018.
 */

public class NotNumberValidator implements ConstraintValidator<NotNumberConstraint, String> {

    @Override
    public void initialize(NotNumberConstraint notNumberConstraint) {

    }

    @Override
    public boolean isValid(String text, ConstraintValidatorContext cxt) {

        if (text != null && text.matches("^\\D*$")){
            return true;
        }
        return false;
    }
}
