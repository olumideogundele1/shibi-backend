package com.shibi.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shibi.app.enums.OrderStatusType;
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
@Table(name = "order_report")
public class OrderReport extends SuperModel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long reportId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatusType type;

    @JsonIgnore
    @OneToOne(fetch= FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(),type);
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
        OrderReport report = (OrderReport) object;
        return
                Objects.equals(type,report.type);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderReport{");
        sb.append("type=").append(type);
        sb.append('}');
        return sb.toString();
    }


}
