package com.shibi.app.usermanagement.controller;

import com.shibi.app.dto.OrdersRequest;
import com.shibi.app.dto.Response;
import com.shibi.app.models.*;
import com.shibi.app.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by User on 30/07/2018.
 */
@RestController
@RequestMapping(value = "orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderAddressService addressService;

    @Autowired
    private OrderPaymentService paymentService;

    @Autowired
    private OrderReportService reportService;

    @Autowired
    private OrderDetailService detailService;


    @GetMapping(value = "/{id}")
    public ResponseEntity getOrder(@PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(orderService.findById(id));
    }


    @GetMapping(value = "/all-orders" )
    public ResponseEntity getAllOrders(@Valid @NotNull(message = "page number is required") @RequestParam int pageNumber,
                                        @Valid @NotNull(message = "page size is required") @RequestParam int pageSize) throws Exception {
        return ResponseEntity.ok(orderService.findAll(new PageRequest(pageNumber, pageSize)));
    }

    @PostMapping(value = "/create-orders")
    public ResponseEntity createOrders(@RequestBody OrdersRequest request) throws Exception {

        Stores stores = storeService.findById(request.getOrderId());


        OrderDetail detail = detailService.getOrderDetail(request.getOrderDetailId());

        if(stores == null){
            return new ResponseEntity(new Response("Please choose a store"), HttpStatus.BAD_REQUEST);
        }

        Orders orders = new Orders();
        orders.setOrderRefNo(request.getOrderRefNo());
        orders.setCustomerName(request.getCustomerName());
        orders.setCustomerEmail(request.getCustomerEmail());
        orders.setPhoneNumber(request.getPhoneNumber());
        orders.setDeliveryFee(request.getDeliveryFee());
        orders.setSubTotal(request.getSubTotal());
        orders.setOrderAmount(request.getOrderAmount());

        orders.setOrderDetail((List<OrderDetail>) detail);
        orders.setStores(stores);

        return ResponseEntity.ok(orders);

    }

    @GetMapping(value = "/{id}/delete")
    public ResponseEntity deleteOrder(@PathVariable(name = "id") Long id) {
         orderService.deleteOrder(id);

         return new ResponseEntity(new Response("order is deleted successfully"), HttpStatus.OK);
    }

}
