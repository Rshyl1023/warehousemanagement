package com.example.demo.controller;

import com.example.demo.dto.IoBatchRequest;
import com.example.demo.dto.IoQueryDTO;
import com.example.demo.dto.IoRequest;
import com.example.demo.dto.WarehouseRecordDTO;
import com.example.demo.service.IoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/io")
public class IoController {

    @Autowired
    private IoService ioService;

    /**
     * 查询进出仓记录
     */
    @GetMapping("/records")
    public List<WarehouseRecordDTO> getWarehouseRecords() {
        return ioService.getWarehouseRecords();
    }

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

    /**
     * 查询进出仓单据
     * POST /api/io/query
     * {
     *   "startDate": "2023-01-01",
     *   "endDate": "2023-12-31",
     *   "materialCode": "M001",
     *   "operatorCode": "P001",
     *   "remark": "测试",
     *   "type": "IN"  // 进出仓类型("IN"或"OUT")，如果为空则查询所有类型
     * }
     */
    @PostMapping("/query")
    public List<Map<String, Object>> queryIoHeaders(@RequestBody IoQueryDTO queryDTO) {
        return ioService.queryIoHeaders(queryDTO);
    }
}
