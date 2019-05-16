package com.shibi.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shibi.app.enums.QuantityType;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by User on 05/07/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "products")
public class Product extends SuperModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_quantity")
    private int productQuantity;

    @Enumerated(EnumType.STRING)
    private QuantityType quantityType;

    private boolean enabled = true;

    @Column(name = "sale_price")
    private double salePrice;

    @Column(name = "list_price")
    private double list_price;

    @Column(name= "product_image" )
    private String productImage;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="store_id")
    private Stores stores;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

//    @JsonManagedReference
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "orderDetail_id")
//    private OrderDetail orderDetail;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<OrderProduct> orderProducts = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), productName,quantityType,salePrice,productImage,enabled);
    }

    @Override
    public boolean equals(Object object) {
        if(this == object){
            return true;
        }
        if(object == null || getClass() != object.getClass()){
            return false;
        }
        if(!super.equals(object)){
            return false;
        }
        Product product = (Product) object;
        return enabled == product.enabled &&
                Objects.equals(productName,product.productName) &&
                Objects.equals(quantityType,product.quantityType) &&
                Objects.equals(salePrice,product.salePrice) &&
                Objects.equals(productImage,product.productImage);

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("productName='").append(productName).append('\'');
        sb.append(", quantityType='").append(quantityType).append('\'');
        sb.append(", salePrice=").append(salePrice);
        sb.append(", productImage=").append(productImage);
        sb.append(", enabled=").append(enabled);
        sb.append('}');
        return sb.toString();
    }
}
