package com.shibi.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by User on 28/07/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "order_paymentMethod")
public class OrderPaymentMethod extends SuperModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long paymentId;

    @Column(name = "transaction_method")
    private String transactionMethod;

    @JsonIgnore
    @OneToOne(fetch= FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(),transactionMethod);
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
        OrderPaymentMethod paymentMethod = (OrderPaymentMethod) object;
        return
                Objects.equals(transactionMethod,paymentMethod.transactionMethod);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderPaymentMethod{");
        sb.append("transactionMethod=").append(transactionMethod);
        sb.append('}');
        return sb.toString();
    }
}
