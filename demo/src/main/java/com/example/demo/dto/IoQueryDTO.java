package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IoQueryDTO {
    private LocalDate startDate;     // 起始日期
    private LocalDate endDate;       // 结束日期
    private String materialCode;     // 物料代码
    private String operatorCode;     // 操作人员
    private String remark;           // 备注
    private String type;             // 进出仓类型("IN"或"OUT")，如果为空则查询所有类型
}