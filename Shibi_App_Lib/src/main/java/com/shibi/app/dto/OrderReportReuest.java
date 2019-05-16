package com.shibi.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shibi.app.enums.OrderStatusType;
import com.shibi.app.enums.QuantityType;
import com.shibi.app.errorHandler.EnumConstraint;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by User on 30/07/2018.
 */
@Data
public class OrderReportReuest {

    private Long id;

    @NotNull(message = "type can be NULL. it should be either DELIVERED or PENDING or FAILED")
    @EnumConstraint(enumClass = OrderStatusType.class, message = "Invalid type. DELIVERED or PENDING or FAILED")
    @JsonProperty("Type")
    private String type;

    @NotNull(message = "order id is required")
    private Long orderId;
}
