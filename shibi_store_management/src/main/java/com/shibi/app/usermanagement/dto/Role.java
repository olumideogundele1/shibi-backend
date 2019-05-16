package com.shibi.app.usermanagement.dto;

import com.shibi.app.enums.UserType;
import com.shibi.app.errorHandler.NotNumberConstraint;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by User on 14/06/2018.
 */
@Data
public class Role {

    private Long id;

    @NotBlank(message = "Role name is required")
    @NotNumberConstraint(message = "Invalid Role Name. Role name cannot contain digit.")
    private String name;

//    @NotNull(message = "user type is required.")
//    private UserType userType;
}

