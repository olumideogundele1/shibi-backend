package com.shibi.app.dto;

import lombok.Data;

/**
 * Created by User on 06/06/2018.
 */
@Data
public class SuperResponse {

    public boolean status;
    public String message;
    public Object data;
}
