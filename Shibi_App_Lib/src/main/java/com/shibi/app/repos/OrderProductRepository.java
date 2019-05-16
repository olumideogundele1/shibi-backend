package com.shibi.app.repos;

import com.shibi.app.models.OrderDetail;
import com.shibi.app.models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by User on 10/08/2018.
 */
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {

    @Query("select o from OrderProduct o where o.product.productId = :id")
    OrderProduct getByProductId(@Param("id") Long id);


}
