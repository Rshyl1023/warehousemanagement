package com.example.demo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class MonthlyIoDTO {
    @ExcelProperty("单号")
    private String ioNo;

    @ExcelProperty("日期")
    private String date;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("物料编码")
    private String materialCode;

    @ExcelProperty("数量")
    private Integer qty;

    @ExcelProperty("操作人")
    private String operatorCode;

    @ExcelProperty("经手人")
    private String handlerCode;
}
