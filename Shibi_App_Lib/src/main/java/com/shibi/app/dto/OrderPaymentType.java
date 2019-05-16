package com.shibi.app.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by User on 30/07/2018.
 */
@Data
public class OrderPaymentType {

    private Long paymentId;

    @NotNull(message = "transaction method is required")
    private String transactionMethod;

    @NotNull(message = "order id is required")
    private Long orderId;
}
