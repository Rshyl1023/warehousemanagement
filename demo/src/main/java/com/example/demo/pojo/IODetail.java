package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IODetail {
    private Integer DETAIL_ID;
    private String IO_NO;
    private String M_CODE;
    private Integer QTY;
}