package com.shibi.app.errorHandler;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by User on 22/06/2018.
 */
public class EnumValidator implements ConstraintValidator<EnumConstraint, String> {

    private EnumConstraint annotation;

    @Override
    public void initialize(EnumConstraint enumConstraint) {
        this.annotation = enumConstraint;
    }

    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {

        boolean result = false;

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if(enumValues != null)
        {
            for(Object enumValue:enumValues)
            {
                if(valueForValidation.equals(enumValue.toString())
                        || (this.annotation.ignoreCase() && valueForValidation.equalsIgnoreCase(enumValue.toString())))
                {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }


}
