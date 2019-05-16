package com.shibi.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by User on 28/07/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "orders")
public class Orders  extends SuperModel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long orderId;

    private String orderRefNo;
    private boolean enabled = true;
    private String customerName;
    private String customerEmail;
    private String phoneNumber;
    private BigDecimal deliveryFee;
    private BigDecimal subTotal;
    private BigDecimal orderAmount;

    @OneToOne(mappedBy = "orders")
    private OrderAddress orderAddress;

    @OneToOne(mappedBy = "orders")
    private OrderPaymentMethod paymentMethod;

    @OneToOne(mappedBy = "orders")
    private OrderReport report;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<OrderDetail> orderDetail;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="store_id")
    private Stores stores;

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(),orderRefNo,customerName, customerEmail,deliveryFee,subTotal,orderAmount,enabled);
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
        Orders orders = (Orders) object;
        return orderRefNo == orders.orderRefNo &&
                enabled == orders.enabled &&
                Objects.equals(customerName,orders.customerName) &&
                Objects.equals(customerEmail,orders.customerEmail) &&
                Objects.equals(deliveryFee,orders.deliveryFee) &&
                Objects.equals(subTotal,orders.subTotal) &&
                Objects.equals(orderAmount,orders.orderAmount);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Orders{");
        sb.append("orderRefNo='").append(orderRefNo).append('\'');
        sb.append(", customerName='").append(customerName).append('\'');
        sb.append(", customerEmail=").append(customerEmail);
        sb.append(", deliveryFee=").append(deliveryFee);
        sb.append(", subTotal=").append(subTotal);
        sb.append(", orderAmount=").append(orderAmount);
        sb.append(", enabled=").append(enabled);
        sb.append('}');
        return sb.toString();
    }



}
