package com.shibi.app.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by User on 07/06/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    @NotNull(message = "id is required")
    private Long id;

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "first name is required")
    private String firstName;

    @NotBlank(message = "last name is required")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email
    private String email;

    @NotBlank(message = "phone number is required")
    private String phone;

    @NotNull(message = "role id is required")
    private Long roleId;
}
