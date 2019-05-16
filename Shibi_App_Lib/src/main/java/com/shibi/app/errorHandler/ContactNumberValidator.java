package com.shibi.app.errorHandler;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by User on 22/06/2018.
 */
public class ContactNumberValidator implements ConstraintValidator <ContactNumberConstraint, String> {

    @Override
    public void initialize(ContactNumberConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext cxt) {

        if (contactField != null && contactField.startsWith("+") && (contactField.length() == 14)){
            return true;
        }else if (contactField != null && (contactField.length() == 11)){
            return true;
        }
        return false;
    }
}
