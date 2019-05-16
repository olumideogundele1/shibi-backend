package com.shibi.app.services.impl;

import com.shibi.app.models.OrderDetail;
import com.shibi.app.models.OrderProduct;
import com.shibi.app.models.Orders;
import com.shibi.app.models.Product;
import com.shibi.app.repos.OrderDetailRepository;
import com.shibi.app.repos.OrderProductRepository;
import com.shibi.app.repos.OrdersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by User on 30/07/2018.
 */
@Service
public class OrderDetailService {

    private Logger logger = LoggerFactory.getLogger(OrderDetailService.class);

    @Autowired
    private OrderDetailRepository detailRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    //create cartItem


    public OrderDetail addProductToCartItem(Product product, Long qty, Orders order) {
        List<OrderDetail> request = (List<OrderDetail>) detailRepository.findOne(order.getOrderId());

            for(OrderDetail cartItem : request){
                if(product.getProductId() == cartItem.getProduct().getProductId()){
                    cartItem.setQty(cartItem.getQty()+qty);
                    cartItem.setOrderPrice(new BigDecimal(product.getSalePrice()).multiply(new BigDecimal(qty)));
                    detailRepository.save(cartItem);
                    return cartItem;
                }
            }

            Orders newOrder = ordersRepository.findOne(order.getOrderId());
            OrderDetail cartItem = new OrderDetail();
            cartItem.setOrders(newOrder);
            cartItem.setProduct(product);
            cartItem.setQty(qty);
            cartItem.setOrderPrice(new BigDecimal(product.getSalePrice()).multiply(new BigDecimal(qty)));
            cartItem = detailRepository.save(cartItem);

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setOrderDetail(cartItem);
        orderProductRepository.save(orderProduct);

        return cartItem;
    }

//    //findByOrders
//    public List<OrderDetail> findByOrders(Orders orders){
//        return detailRepository.findByOrders(orders);
//    }

    //get one
    public OrderDetail getOrderDetail(Long orderDetailId) throws Exception {
        return detailRepository.findOne(orderDetailId);
    }
    //get all
    public Page<OrderDetail> getAll (PageRequest pageRequest) {
        return detailRepository.findAll(pageRequest);
    }


    //save order detail
    public OrderDetail save(OrderDetail orderDetail) {
        return detailRepository.save(orderDetail);
    }


    //delete

    public void deleteOrderDetail(Long id) {
        orderProductRepository.delete(id);
        detailRepository.delete(id);
    }

//  /*  //update shopping
//    public Orders updateCart(Orders orders) {
//        BigDecimal cartTotal = new BigDecimal(0);
//
//    }*/
}
