package com.example.demo.mapper;

import com.example.demo.dto.LedgerDTO;
import com.example.demo.dto.MaterialFlowDTO;
import com.example.demo.dto.MonthlyIoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ReportMapper {
    /**
     * 物料流量统计：按时间范围统计每个物料的进出仓总量
     */
    List<MaterialFlowDTO> getMaterialFlowData(@Param("startDate") LocalDate startDate, 
                                               @Param("endDate") LocalDate endDate);

    /**
     * 月度进出仓单明细
     */
    List<MonthlyIoDTO> getMonthlyIoData(@Param("year") int year, 
                                        @Param("month") int month);

    /**
     * 仓库账本：按年份和物料代码查询该物料的进出仓记录
     */
    List<LedgerDTO> getLedgerData(@Param("year") int year, 
                                  @Param("materialCode") String materialCode);
}
