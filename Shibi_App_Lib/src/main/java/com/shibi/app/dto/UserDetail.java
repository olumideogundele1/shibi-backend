package com.shibi.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 06/06/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {

    private Long id;

    private String username;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;
    private boolean enabled;
    private String sessionId;

        List<String> roles = new ArrayList<>();
}
