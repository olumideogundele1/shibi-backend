package com.shibi.app.services.impl;

import com.shibi.app.models.OrderReport;
import com.shibi.app.repos.OrderReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by User on 30/07/2018.
 */
@Service
public class OrderReportService {

    @Autowired
    private OrderReportRepository reportRepository;


    //getOne Report
    public OrderReport getReport(Long orderId) throws Exception {
        return reportRepository.findOne(orderId);
    }
    //get All report
    public Page<OrderReport> getallReport(PageRequest pageRequest) {
        return reportRepository.findAll(pageRequest);
    }

    //save a report
    public OrderReport save(OrderReport report) {
        return reportRepository.save(report);
    }


    //delete a report
    public void deleteReport(Long id) {
         reportRepository.delete(id);
    }
}
