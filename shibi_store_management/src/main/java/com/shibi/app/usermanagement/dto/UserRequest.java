package com.shibi.app.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shibi.app.enums.UserType;
import com.shibi.app.errorHandler.ContactNumberConstraint;
import com.shibi.app.errorHandler.EnumConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by User on 15/06/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private Long id;
    private String firstName;
    private String lastName;


    @ContactNumberConstraint
    @NotBlank(message = "phone number cannot be empty")
    @JsonProperty("PhoneNumber")
    private String phoneNumber;

    private String userName;

    @NotBlank(message = "Email address is required")
    @Email(message = "invalid email address")
    private String email;


//    @NotNull(message = "password is required")
//    private String password;

//    @NotNull(message = "type can be NULL. it should be either SUPERADMIN or ADMIN or CLIENT or CUSTOMERS")
//    @EnumConstraint(enumClass = UserType.class, message = "Invalid type. it should be either SUPERADMIN or ADMIN or CLIENT or CUSTOMERS")
//    @JsonProperty("Type")
//    private String type;

//    @NotBlank(message = "role id is required")
//    private Long roleId;
}
