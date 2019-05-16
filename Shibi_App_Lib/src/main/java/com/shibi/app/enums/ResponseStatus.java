package com.shibi.app.enums;

import java.util.HashMap;

/**
 * Created by User on 06/06/2018.
 */
public enum ResponseStatus {

    REQUIRED,
    OPTIONAL;

    public static HashMap getAll(){
        HashMap<String, ResponseStatus> alertTypes = new HashMap<>();
        for(ResponseStatus alertType : ResponseStatus.values()){
            alertTypes.put(alertType.toString().toLowerCase(), alertType);
        }

        return alertTypes;
    }
}
