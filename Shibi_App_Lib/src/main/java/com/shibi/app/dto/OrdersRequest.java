package com.shibi.app.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by User on 30/07/2018.
 */
@Data
public class OrdersRequest {

    private Long orderId;

    private String orderRefNo;

    private String customerName;

    private String customerEmail;

    private String phoneNumber;


     private BigDecimal deliveryFee;
     private BigDecimal subTotal;
     private BigDecimal orderAmount;
    @NotNull(message = "Store is required")
    private Long storeId;

    private Long orderAdrressId;
    private Long orderPaymentMethodId;
    private Long orderReportId;
    private Long orderDetailId;
}
