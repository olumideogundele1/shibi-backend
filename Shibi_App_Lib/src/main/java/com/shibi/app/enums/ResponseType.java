package com.shibi.app.enums;

import java.util.HashMap;

/**
 * Created by User on 06/06/2018.
 */
public enum  ResponseType {

    TEXT,
    OPTIONS;

    public static HashMap getAll(){
        HashMap<String, ResponseType> alertTypes = new HashMap<>();
        for(ResponseType alertType : ResponseType.values()){
            alertTypes.put(alertType.toString().toLowerCase(), alertType);
        }

        return alertTypes;
    }
}
