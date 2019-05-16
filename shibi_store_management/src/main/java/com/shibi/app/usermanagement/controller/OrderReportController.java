package com.shibi.app.usermanagement.controller;

import com.shibi.app.dto.OrderReportReuest;
import com.shibi.app.enums.OrderStatusType;
import com.shibi.app.models.OrderReport;
import com.shibi.app.models.Orders;
import com.shibi.app.services.impl.OrderReportService;
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
@RequestMapping(value = "order-reports")
public class OrderReportController {

    @Autowired
    private OrderReportService reportService;

    @Autowired
    private OrderService orderService;


    @GetMapping(value = "/{id}")
    public ResponseEntity getOrderAddress(@PathVariable(name = "id") Long id) throws Exception {

        return ResponseEntity.ok(reportService.getReport(id));
    }


    @GetMapping(value = "/all-reports" )
    public ResponseEntity getAllAddress(@Valid @NotNull(message = "page number is required") @RequestParam int pageNumber,
                                        @Valid @NotNull(message = "page size is required") @RequestParam int pageSize) throws Exception {
        return ResponseEntity.ok(reportService.getallReport(new PageRequest(pageNumber, pageSize)));
    }

    @PostMapping(value = "/create-order-report")
    public ResponseEntity createOrderReport(@RequestBody OrderReportReuest reportRequest) throws Exception {

        Orders orders = orderService.findById(reportRequest.getId());

        OrderReport report = new OrderReport();
        report.setType(OrderStatusType.valueOf(reportRequest.getType()));
        report.setOrders(orders);

        return ResponseEntity.ok(report);
    }
}
