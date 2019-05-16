package com.shibi.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shibi.app.enums.QuantityType;
import com.shibi.app.errorHandler.EnumConstraint;
import com.shibi.app.models.Stores;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by User on 05/07/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private Long id;

    @NotBlank(message = "product nsme is required")
    private String productName;

//    @NotBlank(message = "product quantity is required")
//    private int productQuantity;

//    @NotBlank(message = "product quantity type is required")
//    private QuantityType quantityType;

    @NotNull(message = "type can be NULL. it should be either SPOON or UNIT")
    @EnumConstraint(enumClass = QuantityType.class, message = "Invalid type. SPOON or UNIT")
    @JsonProperty("Type")
    private String type;


    private boolean enabled;

    @NotNull(message = "product sale price is required")
    private double salePrice;

    //@NotBlank(message = "product list price is required")
    private double listPrice;

    @NotNull
    private String productImage;


    @NotNull(message = "Store is required")
    private Long storeId;

    @NotNull(message = "Category is required")
    private Long categoryId;


}
