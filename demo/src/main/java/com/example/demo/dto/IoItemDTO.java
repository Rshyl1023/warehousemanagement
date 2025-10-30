package com.example.demo.dto;

import lombok.Data;

/**
 * 单个物料的进出仓明细
 */
@Data
public class IoItemDTO {
    private String materialCode;  // 物料代码
    private String type;          // "IN" 或 "OUT"
    private Integer qty;          // 数量
}
