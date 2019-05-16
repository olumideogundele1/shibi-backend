package com.shibi.app.usermanagement.enums;

/**
 * Created by User on 07/06/2018.
 */
public enum Success {

    SUCCESS("successful");

    private String value;

    Success(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
