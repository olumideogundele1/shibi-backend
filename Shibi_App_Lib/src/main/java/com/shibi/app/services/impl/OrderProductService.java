package com.shibi.app.services.impl;

import com.shibi.app.models.OrderProduct;
import com.shibi.app.repos.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by User on 10/08/2018.
 */
public class OrderProductService {

    private OrderProductRepository orderProductRepository;

    @Autowired
    private void setOrderProductRepository(OrderProductRepository orderProductRepository){
        this.orderProductRepository = orderProductRepository;
    }

    public OrderProduct getOrdersByProductId(Long id) {
        return orderProductRepository.getByProductId(id);
    }

    public OrderProduct save(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }
}
