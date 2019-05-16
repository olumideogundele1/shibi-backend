package com.shibi.app.repos;

import com.shibi.app.models.OrderAddress;
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
public interface OrderAddressRepository extends JpaRepository<OrderAddress, Long> {

    Page<OrderAddress> findAll(Pageable pageable);

    @Query("select a from OrderAddress a where a.orderAddressId = :orderAddressId")
    OrderAddress findOne(@Param("orderAddressId") Long orderAddressId);
}
