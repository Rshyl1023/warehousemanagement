package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class WarehouseRecordDTO {
    private String ioNo;
    private String materialCode;
    private String materialName;
    private String spec;
    private String unit;
    private String type;
    private Integer qty;
    private String operatorName;
    private String handlerName;
    private LocalDate date;
}
