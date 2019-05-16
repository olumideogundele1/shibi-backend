package com.shibi.app.enums;

/**
 * Created by User on 30/07/2018.
 */
public enum OrderStatusType {

    DELIVERED ("delivered"),
    PENDING ("pending"),
    FAILED ("failed");

    private String value;

    OrderStatusType (String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
