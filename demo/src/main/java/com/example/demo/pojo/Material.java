package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Material {
    private String M_CODE;
    private String M_NAME;
    private String M_SPEC;
    private String M_UNIT;
    private Integer M_STOCK_QTY;
    private Integer M_MIN_STOCK;
    private String M_OTHER;
}