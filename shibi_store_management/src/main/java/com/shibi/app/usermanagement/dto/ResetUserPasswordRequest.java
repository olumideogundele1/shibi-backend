package com.shibi.app.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by User on 14/06/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetUserPasswordRequest {

    @NotBlank(message = "No email address provided")
    private String email;
    @NotBlank(message="No path provided")
    private String path;
}
