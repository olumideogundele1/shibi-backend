package com.shibi.app.services.impl;

import com.shibi.app.models.Orders;
import com.shibi.app.repos.OrdersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by User on 30/07/2018.
 */
@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrdersRepository ordersRepository;

    //get one order
    public Orders findById (Long orderId) throws Exception {
        return ordersRepository.findOne(orderId);
    }


    //get all orders
    public Page<Orders> findAll(PageRequest pageRequest) {
        return ordersRepository.findAll(pageRequest);
    }


    //save orders
    public Orders save (Orders orders) {
        return ordersRepository.save(orders);
    }

    //delete orders
    public void deleteOrder(Long id) {
        ordersRepository.delete(id);
    }
}
