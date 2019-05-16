package com.shibi.app.repos;

import com.shibi.app.models.OrderDetail;
import com.shibi.app.models.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by User on 30/07/2018.
 */

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {


    Page<OrderDetail> findAll(Pageable pageable);



    @Query("select o from OrderDetail o where o.orderDetailId = :orderDetailId")
    OrderDetail findOne(@Param("orderDetailId") Long orderDetailId);
}
