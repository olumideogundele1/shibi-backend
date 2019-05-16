package com.shibi.app.usermanagement.controller;

import com.shibi.app.dto.OrderAddressRequest;
import com.shibi.app.models.OrderAddress;
import com.shibi.app.models.Orders;
import com.shibi.app.services.impl.OrderAddressService;
import com.shibi.app.services.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by User on 30/07/2018.
 */
@RestController
@RequestMapping(value = "order-address")
public class OrderAddressController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderAddressService orderAddressService;

    @GetMapping(value = "/{id}")
    public ResponseEntity getOrderAddress(@PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(orderAddressService.getAddress(id));
    }

    @GetMapping(value = "/all-address" )
    public ResponseEntity getAllAddress(@Valid @NotNull(message = "page number is required") @RequestParam int pageNumber,
                                      @Valid @NotNull(message = "page size is required") @RequestParam int pageSize) throws Exception {
        return ResponseEntity.ok(orderAddressService.getAllAddress(new PageRequest(pageNumber,pageSize)));
    }

    @PostMapping(value = "/create-address")
    public ResponseEntity createAddress(@RequestBody OrderAddressRequest request) throws Exception {

        Orders orders = orderService.findById(request.getOrderAddressId());
        OrderAddress address = new OrderAddress();
        address.setOrderRegion(request.getOrderRegion());
        address.setOrders(orders);

        orderAddressService.save(address);
        return ResponseEntity.ok(address);

    }



}
