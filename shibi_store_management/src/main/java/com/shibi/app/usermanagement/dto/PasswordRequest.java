package com.shibi.app.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by User on 13/06/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRequest {

    @NotBlank(message = "current password is required")
    private String currentPassword;

    @NotBlank(message = "new password is required")
    private String newPassword;

    @NotBlank(message = "confirm password is required")
    private String confirmPassword;

//    private String email;
}
