package com.shibi.app.usermanagement.enums;

/**
 * Created by User on 07/06/2018.
 */
public enum  Errors {

    INVALID_REQUEST("Invalid request provided."),
    ID_IS_NULL("Id is null"),
    ROLE_NOT_FOUND("Provided role ids does not exist."),
    UNKNOWN_USER("unknown user."),
    EXPIRED_SESSION("Expired Session"),
    EXPIRED_TOKEN("Expired Token"),
    ROLE_ALREADY_EXIST("Role name '{}' already exist"),
    NOT_PERMITTED("Permission not granted.");

    private String value;

    Errors(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
