package com.shibi.app.enums;

/**
 * Created by User on 06/06/2018.
 */
public enum UserType {

    SUPERADMIN("SUPERADMIN"),
    ADMIN("ADMIN"),
    CLIENT("CLIENT"),
    CUSTOMERS("CUSTOMERS"),
    UNKNOWN("UNKNOWN");
    String value;

    UserType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static synchronized UserType find(String userType){
        try {
            return UserType.valueOf(userType);
        } catch (Exception e) {
            return findByValue(userType);
        }
    }

    private static UserType findByValue(String value) {
        UserType type = null;

        for (UserType userType : UserType.values()) {
            if( userType.value.equalsIgnoreCase(value)) {
                type = userType;
                break;
            }
        }

        return  type == null ? UNKNOWN : type;


    }

}
