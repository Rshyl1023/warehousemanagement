package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Material {
    @TableId(type = IdType.ASSIGN_ID)
    private String materialCode;     // 原 M_CODE
    private String materialName;     // 原 M_NAME
    private String spec;             // 原 M_SPEC（"spec" 已足够清晰）
    private String unit;             // 原 M_UNIT
    private Integer stockQty;        // 原 M_STOCK_QTY
    private Integer minStock;        // 原 M_MIN_STOCK
    private String other;            // 原 M_OTHER
}