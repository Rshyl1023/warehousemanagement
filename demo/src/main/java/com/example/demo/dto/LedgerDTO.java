package com.example.demo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class LedgerDTO {
    @ExcelProperty("日期")
    private String date;

    @ExcelProperty("进仓数量")
    private Integer inQty;

    @ExcelProperty("出仓数量")
    private Integer outQty;

    @ExcelProperty("当日结存")
    private Integer balance;
}
