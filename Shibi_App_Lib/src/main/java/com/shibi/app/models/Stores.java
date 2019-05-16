package com.shibi.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shibi.app.enums.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Created by User on 03/07/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", Constants.CREATED_AT, Constants.UPDATED_AT})
@Entity
@Table(name = "stores")
public class Stores extends SuperModel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long storeId;

    @Column(name = "store_name")
    private String storeName;

    private boolean enabled = true;

    @Column(name = "store_location")
    private String storeLocation;

    @Column(name = "store_image")
    private String storeImage;

    @JsonIgnore
    @OneToOne(fetch= FetchType.EAGER,
                cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @OneToMany(mappedBy = "stores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<Product> products;

    @OneToMany(mappedBy = "stores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<Orders> orders;


    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(),storeName,storeLocation,storeImage,enabled);
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
        Stores stores = (Stores) object;
        return  enabled == stores.enabled &&
                Objects.equals(storeName,stores.storeName) &&
                Objects.equals(storeLocation,stores.storeLocation) &&
                Objects.equals(storeImage,stores.storeImage);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Stores{");
        sb.append("storeName='").append(storeName).append('\'');
        sb.append(", storeLocation='").append(storeLocation).append('\'');
        sb.append(", storeImage='").append(storeImage).append('\'');
        sb.append(", enabled=").append(enabled);
        sb.append('}');
        return sb.toString();
    }

}
