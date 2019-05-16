package com.shibi.app.repos;

import com.shibi.app.models.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



/**
 * Created by User on 30/07/2018.
 */
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("select o from Orders o where o.orderId = :orderId ")
    Orders findOne(@Param("orderId") Long orderId );

    Page<Orders> findAll(Pageable pageable);
}
