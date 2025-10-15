package com.example.demo.dto;

import lombok.Data;

@Data
public class IoRequest {
    private String type; // "IN" 或 "OUT"
    private String operatorCode;
    private String handlerCode;
    private String materialCode;
    private Integer qty;
    private String remark;
}
