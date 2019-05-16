package com.shibi.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by User on 28/07/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "orderAddress")
public class OrderAddress extends SuperModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long orderAddressId;

    @NotNull
    @Column(name = "order_region")
    private String orderRegion;

    @JsonIgnore
    @OneToOne(fetch= FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(),orderRegion);
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
        OrderAddress address = (OrderAddress) object;
        return
                Objects.equals(orderRegion,address.orderRegion);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderAddress{");
       sb.append("orderRegion=").append(orderRegion);
        sb.append('}');
        return sb.toString();
    }
}
