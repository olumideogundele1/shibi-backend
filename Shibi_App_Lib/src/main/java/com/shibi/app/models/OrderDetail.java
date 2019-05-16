package com.shibi.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by User on 28/07/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "order_detail")
public class OrderDetail extends SuperModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long orderDetailId;

    private BigDecimal orderPrice;

    private Long qty;

    @OneToOne
    @JsonIgnore
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch= FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @OneToMany(mappedBy = "orderDetail", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), orderPrice);
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object == null || getClass() != object.getClass()){
            return false;
        }
        if(!super.equals(object)){
            return false;
        }
        OrderDetail detail = (OrderDetail) object;
        return
                Objects.equals(orderPrice,detail.orderPrice);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderAddress{");
        sb.append("orderPrice=").append(orderPrice);
        sb.append('}');
        return sb.toString();
    }
}
