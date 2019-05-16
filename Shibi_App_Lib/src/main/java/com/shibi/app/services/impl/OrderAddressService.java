package com.shibi.app.services.impl;

import com.shibi.app.models.OrderAddress;
import com.shibi.app.repos.OrderAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by User on 30/07/2018.
 */
@Service
public class OrderAddressService  {

    @Autowired
    private OrderAddressRepository addressRepository;

    //get one
    public OrderAddress getAddress(Long orderAddressId) throws Exception {
        return addressRepository.findOne(orderAddressId);
    }

    //get all
    public Page<OrderAddress> getAllAddress(PageRequest pageRequest) {
        return addressRepository.findAll(pageRequest);
    }


    //save
    public OrderAddress save(OrderAddress orderAddress) {
        return addressRepository.save(orderAddress);
    }

    //delete
    public void deleteAddress(Long id){
        addressRepository.delete(id);
    }

}
