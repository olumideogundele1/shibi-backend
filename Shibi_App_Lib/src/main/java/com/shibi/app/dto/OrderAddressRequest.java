package com.shibi.app.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by User on 30/07/2018.
 */
@Data
public class OrderAddressRequest {

    private Long orderAddressId;

    @NotNull(message = "region is required to complete this process")
    private String orderRegion;

    @NotNull(message = "order id is required")
    private Long orderId;
}
