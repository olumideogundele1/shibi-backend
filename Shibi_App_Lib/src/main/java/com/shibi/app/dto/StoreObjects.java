package com.shibi.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shibi.app.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

/**
 * Created by User on 04/07/2018.
 */
@Data
@AllArgsConstructor

public class StoreObjects {

    private Long storeId;
    private String storeName;
    private String storeLocation;


    private String storeImage;


    @NotNull(message = "user id is required")
    private Long userId;
}
