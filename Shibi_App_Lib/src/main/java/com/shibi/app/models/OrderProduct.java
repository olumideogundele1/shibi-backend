package com.shibi.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by User on 31/07/2018.
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "order_product" )
public class OrderProduct implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderProductId;

    public OrderProduct() {

    }

    public OrderProduct(OrderDetail orderDetail, Product product) {
        this.orderDetail = orderDetail;
        this.product = product;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderDetailId")
    private OrderDetail orderDetail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    private Product product;
}
