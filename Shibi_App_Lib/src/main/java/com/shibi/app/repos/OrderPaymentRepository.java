package com.shibi.app.repos;

import com.shibi.app.models.OrderPaymentMethod;
import com.shibi.app.models.OrderReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by User on 30/07/2018.
 */
@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPaymentMethod, Long>{

    @Query("select p from  OrderPaymentMethod p where p.paymentId = :paymentId")
    OrderPaymentMethod findOne(@Param("paymentId") Long paymentId);

    Page<OrderPaymentMethod> findAll(Pageable pageable);
}
