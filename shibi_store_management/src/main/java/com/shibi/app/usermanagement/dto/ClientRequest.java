package com.shibi.app.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by User on 21/06/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {

    private Long id;
    private String firstName;
    private String lastName;

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "Email address is required")
    @Email(message = "invalid email address")
    private String email;

    @NotBlank(message = "phone number is requisred")
    private String phoneNumber;
}
