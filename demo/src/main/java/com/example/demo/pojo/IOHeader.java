package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IOHeader {
    @TableId(type = IdType.ASSIGN_ID)
    private String ioNo;             // 原 IO_NO
    private LocalDate ioDate;        // 原 IO_DATE
    private String ioType;           // 原 IO_TYPE
    private String operatorCode;     // 原 OPERATOR_CODE
    private String handlerCode;      // 原 HANDLER_CODE
    private String remark;           // 原 REMARK
}