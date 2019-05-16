package com.shibi.app.usermanagement.controller;

import com.shibi.app.dto.OrderPaymentType;
import com.shibi.app.models.OrderPaymentMethod;
import com.shibi.app.models.Orders;
import com.shibi.app.services.impl.OrderPaymentService;
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
@RequestMapping(value = "order-paymentMethod")
public class OrderPaymentController {

    @Autowired
    private OrderPaymentService paymentService;

    @Autowired
    private OrderService orderService;


    @GetMapping(value = "/{id}")
    public ResponseEntity getOrderPayment(@PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(paymentService.getPaymentMethod(id));
    }


    @GetMapping(value = "/all-paymentMethod" )
    public ResponseEntity getAllPayment(@Valid @NotNull(message = "page number is required") @RequestParam int pageNumber,
                                        @Valid @NotNull(message = "page size is required") @RequestParam int pageSize) throws Exception {
        return ResponseEntity.ok(paymentService.findAllMethod(new PageRequest(pageNumber, pageSize)));
    }

    @PostMapping(value = "/create-PaymentMethod")
    public ResponseEntity createPaymentMethod(@RequestBody OrderPaymentType request) throws Exception {
        Orders orders = orderService.findById(request.getPaymentId());

        OrderPaymentMethod paymentMethod = new OrderPaymentMethod();
        paymentMethod.setTransactionMethod(request.getTransactionMethod());
        paymentMethod.setOrders(orders);

        paymentService.save(paymentMethod);
        return ResponseEntity.ok(paymentMethod);


    }



}
