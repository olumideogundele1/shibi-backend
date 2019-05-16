package com.shibi.app.usermanagement.controller;

import com.shibi.app.dto.OrderDetailRequest;
import com.shibi.app.models.OrderDetail;
import com.shibi.app.models.OrderProduct;
import com.shibi.app.models.Orders;
import com.shibi.app.models.Product;
import com.shibi.app.repos.OrderDetailRepository;
import com.shibi.app.repos.ProductRepository;
import com.shibi.app.services.impl.OrderDetailService;
import com.shibi.app.services.impl.OrderService;
import com.shibi.app.services.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 30/07/2018.
 */
@RestController
@RequestMapping(value = "orderDetails")
public class OrderDetailController {

    @Autowired
    private OrderDetailService detailService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailRepository detailRepository;


    @GetMapping(value = "/{id}")
    public ResponseEntity getOrderDetail(@PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(detailService.getOrderDetail(id));
    }


    @GetMapping(value = "/all-orderDetails" )
    public ResponseEntity getAllDetails(@Valid @NotNull(message = "page number is required") @RequestParam int pageNumber,
                                        @Valid @NotNull(message = "page size is required") @RequestParam int pageSize) throws Exception {
        return ResponseEntity.ok(detailService.getAll(new PageRequest(pageNumber,pageSize)));
    }

    @PostMapping(value = "/create-OrderDetail")
    public ResponseEntity createOrderDetail(@RequestBody OrderDetailRequest request) throws Exception {

        Orders orders = orderService.findById(request.getRequestId());

        Product product = productService.getProductById(request.getRequestId());
        Long qty = Long.valueOf(0);


        if(qty == null) {
            return new ResponseEntity("Not Enough Stock!", HttpStatus.BAD_REQUEST);
        }
        OrderDetail cartItem = detailService.addProductToCartItem(product,qty,orders);

//        OrderDetail detail = new OrderDetail();
//        detail.setOrderPrice(request.getPrice());
//
//        Product product1 = new Product();
//        product1.setProductId(request.getRequestId());
//        Set<OrderProduct> productSet = new HashSet<>();
//        productSet.add(new OrderProduct(detail,product1));
//        for (OrderProduct op : productSet) {
//            productRepository.save(op.getProduct());
//        }
//        detail.getOrderProducts().addAll(productSet);
//        detail.setOrders(orders);
//        detailService.save(detail);


        return ResponseEntity.ok(cartItem);

    }
}
