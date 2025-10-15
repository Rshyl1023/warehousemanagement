package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IOHeader {
    private String IO_NO;
    private LocalDate IO_DATE;
    private String IO_TYPE;
    private String OPERATOR_CODE;
    private String HANDLER_CODE;
    private String REMARK;
}