package com.example.demo.controller;

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
}
