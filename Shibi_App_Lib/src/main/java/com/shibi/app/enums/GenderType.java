package com.shibi.app.enums;

import java.util.HashMap;

/**
 * Created by User on 06/06/2018.
 */
public enum GenderType {

    MALE,
    FEMALE;

    public static HashMap getAll(){
        HashMap<String, GenderType> genderTypes = new HashMap<>();
        for(GenderType genderType : GenderType.values()){
            genderTypes.put(genderType.toString().toLowerCase(), genderType);
        }

        return genderTypes;
    }
}
