package com.shibi.app.errorHandler;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 22/06/2018.
 */
public class DateFormatValidator implements ConstraintValidator<DateFormatConstraint, String> {

    @Override
    public void initialize(DateFormatConstraint dateFormatContraint) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        try {
            System.out.println("date --> "+s);
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(s);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }
}
