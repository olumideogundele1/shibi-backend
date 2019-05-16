package com.shibi.app.services.impl;

import com.shibi.app.models.OrderPaymentMethod;
import com.shibi.app.repos.OrderPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by User on 30/07/2018.
 */
@Service
public class OrderPaymentService {

    @Autowired
    private OrderPaymentRepository paymentRepository;

    //get one
    public OrderPaymentMethod getPaymentMethod(Long paymentId) throws Exception {
        return paymentRepository.findOne(paymentId);
    }
    //get all
    public Page<OrderPaymentMethod> findAllMethod(PageRequest pageRequest) {
        return paymentRepository.findAll(pageRequest);
    }
    //save method
    public OrderPaymentMethod save(OrderPaymentMethod paymentMethod) {
        return paymentRepository.save(paymentMethod);
    }

    //delete method
    public void deletePayment(Long id) {
        paymentRepository.delete(id);
    }
}
