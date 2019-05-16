package com.shibi.app.enums;

/**
 * Created by User on 05/07/2018.
 */
public enum QuantityType {

    SPOON ("spoon"),
    UNIT ("unit"),
    WRAP ("wrap"),
    BOTTLE ("bottle"),
    PIECE ("piece");

    String value;

    QuantityType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
