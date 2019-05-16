package com.shibi.app.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 30/07/2018.
 */
@Data
public class OrderDetailRequest {

    private Long requestId;
    private int qty;

    @NotNull(message = "order id is required")
    private Long orderId;

    List<String> products = new ArrayList<>();

    @NotNull(message = "price is required")
    private double price;
}
