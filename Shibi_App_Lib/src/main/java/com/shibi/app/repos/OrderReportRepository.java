package com.shibi.app.repos;

import com.shibi.app.dto.OrderReportReuest;
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
public interface OrderReportRepository  extends JpaRepository<OrderReport, Long>{

    Page<OrderReport> findAll(Pageable pageable);

    @Query("select o from OrderReport o where o.reportId = :reportId")
    OrderReport findOne(@Param("reportId") Long reportId);
}
