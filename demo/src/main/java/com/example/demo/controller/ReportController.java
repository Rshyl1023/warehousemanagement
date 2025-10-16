package com.example.demo.controller;

import com.example.demo.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // 物料流量统计
    @GetMapping("/material-flow")
    public void materialFlow(@RequestParam LocalDate startDate,
                             @RequestParam LocalDate endDate,
                             HttpServletResponse response) throws Exception {
        reportService.exportMaterialFlow(response, startDate, endDate);
    }

    // 月度进出仓单
    @GetMapping("/monthly-io")
    public void monthlyIo(@RequestParam int year,
                          @RequestParam int month,
                          HttpServletResponse response) throws Exception {
        reportService.exportMonthlyIo(response, year, month);
    }

    // 仓库账本
    @GetMapping("/ledger")
    public void ledger(@RequestParam int year,
                       @RequestParam String materialCode,
                       HttpServletResponse response) throws Exception {
        reportService.exportLedger(response, year, materialCode);
    }
}
