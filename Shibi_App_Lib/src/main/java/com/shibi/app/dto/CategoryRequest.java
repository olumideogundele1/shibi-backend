package com.shibi.app.dto;

import com.shibi.app.models.Stores;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by User on 05/07/2018.
 */
@Data
@AllArgsConstructor
public class CategoryRequest {

    private Long categoryId;
    private String categoryName;

    private boolean enabled;



}
