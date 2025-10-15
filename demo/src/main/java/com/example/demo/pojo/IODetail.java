package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IODetail {
    @TableId(type = IdType.ASSIGN_ID)
    private Integer detailId;      // 原 DETAIL_ID
    private String ioNo;           // 原 IO_NO
    private String materialCode;   // 原 M_CODE（语义更清晰）
    private Integer qty;           // 原 QTY
}