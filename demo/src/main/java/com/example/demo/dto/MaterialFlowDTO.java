package com.example.demo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class MaterialFlowDTO {
    @ExcelProperty("物料编码")
    private String materialCode;

    @ExcelProperty("物料名称")
    private String materialName;

    @ExcelProperty("入库总量")
    private Integer inQty;

    @ExcelProperty("出库总量")
    private Integer outQty;

    @ExcelProperty("净流量")
    private Integer netFlow;
}
