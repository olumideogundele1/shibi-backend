package com.shibi.app.usermanagement.dto;

import lombok.Data;

/**
 * Created by User on 27/06/2018.
 */
@Data
public class PasswordSetting {

    private int passwordLength;
    private int minSpecialChar;
    private int minLowerCase;
    private int minUppercase;
    private int minDigit;
}
