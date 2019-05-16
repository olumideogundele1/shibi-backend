package com.shibi.app.usermanagement.dto;

import lombok.Data;

/**
 * Created by User on 25/06/2018.
 */
@Data
public class LogOutResponse {

    private boolean status;
    private String message;
    private Object data;
}
