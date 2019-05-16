package com.shibi.app.usermanagement.dto;

import com.shibi.app.dto.SuperResponse;
import com.shibi.app.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by User on 13/06/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse{

//    private Object userDetail;
    private String Token;


}
