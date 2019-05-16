package com.shibi.app.enums;

/**
 * Created by User on 27/06/2018.
 */
public enum Errors {

    INVALID_REQUEST("Invalid request provided."),
    ID_IS_NULL("Id is null"),
    MODULE_NOT_FOUND("Provided Module does not exist."),
    PARENT_TASK_NOT_FOUND("Provided parent task does not exist."),
    GROUP_NOT_FOUND("Provided group id does not exist."),
    EMPTY_GROUP_NAME("Invalid Group Name."),
    TASK_NOT_FOUND("Provided task ids does not exist."),
    UNKNOWN_USER("unknown user."),
    EXPIRED_SESSION("Expired Session"),
    EXPIRED_TOKEN("Expired Token"),
    GROUP_ALREADY_EXIST("Group name '{}' already exist"),
    NOT_PERMITTED("Permission not granted.");


    private String value;

    Errors(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
