package com.example.demo.controller;

import com.example.demo.dto.IoBatchRequest;
import com.example.demo.dto.IoRequest;
import com.example.demo.service.IoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/io")
public class IoController {

    @Autowired
    private IoService ioService;

    /**
     * 进出仓操作
     * POST /api/io/process
     * {
     *   "type": "IN",
     *   "operatorCode": "P001",
     *   "handlerCode": "P002",
     *   "materialCode": "M001",
     *   "qty": 10,
     *   "remark": "测试进仓"
     * }
     */
    @PostMapping("/process")
    public Map<String, Object> processIo(@RequestBody IoRequest request) {
        return ioService.processIo(request);
    }

    /**
     * 批量进出仓操作
     * POST /api/io/batch-process
     * {
     *   "operatorCode": "P001",
     *   "handlerCode": "P002",
     *   "remark": "批量进货",
     *   "items": [
     *     {
     *       "materialCode": "M001",
     *       "type": "IN",
     *       "qty": 100
     *     },
     *     {
     *       "materialCode": "M002",
     *       "type": "OUT",
     *       "qty": 50
     *     }
     *   ]
     * }
     */
    @PostMapping("/batch-process")
    public Map<String, Object> batchProcessIo(@RequestBody IoBatchRequest request) {
        return ioService.processBatchIo(request);
    }
}
