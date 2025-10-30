package com.example.demo.dto;

import lombok.Data;
import java.util.List;

/**
 * 批量进出仓请求
 */
@Data
public class IoBatchRequest {
    private String operatorCode;      // 操作人员代码
    private String handlerCode;       // 经手人代码
    private String remark;            // 备注
    private List<IoItemDTO> items;    // 物料明细列表（可包含多个物料，每个物料可以是IN或OUT）
}
