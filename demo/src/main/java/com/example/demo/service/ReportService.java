package com.example.demo.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.dto.LedgerDTO;
import com.example.demo.dto.MaterialFlowDTO;
import com.example.demo.dto.MonthlyIoDTO;
import com.example.demo.mapper.MaterialMapper;
import com.example.demo.mapper.ReportMapper;
import com.example.demo.pojo.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private MaterialMapper materialMapper;

    // 从数据库查询物料流量数据
    public List<MaterialFlowDTO> getMaterialFlowData(LocalDate start, LocalDate end) {
        return reportMapper.getMaterialFlowData(start, end);
    }

    // 从数据库查询月度进出仓数据
    public List<MonthlyIoDTO> getMonthlyIoData(int year, int month) {
        return reportMapper.getMonthlyIoData(year, month);
    }

    // 从数据库查询仓库账本数据
    public List<LedgerDTO> getLedgerData(int year, String materialCode) {
        return reportMapper.getLedgerData(year, materialCode);
    }

    // 通用：设置响应头
    private void setResponseHeader(HttpServletResponse response, String fileName) throws IOException {
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName + ".xlsx");
    }

    // 导出物料流量统计（含分析行）
    public void exportMaterialFlow(HttpServletResponse response, LocalDate start, LocalDate end) throws IOException {
        List<MaterialFlowDTO> data = getMaterialFlowData(start, end);
        String minFlowName = data.stream()
                .min(Comparator.comparingInt(MaterialFlowDTO::getNetFlow))
                .map(MaterialFlowDTO::getMaterialName)
                .orElse("无");

        setResponseHeader(response, "软工230130号_物料进出仓流量统计");

        List<List<String>> excelData = new ArrayList<>();
        // 表头
        excelData.add(Arrays.asList("物料编码", "物料名称", "入库总量", "出库总量", "净流量"));
        // 数据行
        for (MaterialFlowDTO dto : data) {
            excelData.add(Arrays.asList(
                    dto.getMaterialCode(),
                    dto.getMaterialName(),
                    String.valueOf(dto.getInQty()),
                    String.valueOf(dto.getOutQty()),
                    String.valueOf(dto.getNetFlow())
            ));
        }
        // 分析行
        excelData.add(Arrays.asList("", "【分析】流动量最小的物料：", minFlowName, "", ""));

        try (ExcelWriter writer = EasyExcel.write(response.getOutputStream()).build()) {
            WriteSheet sheet = EasyExcel.writerSheet("物料流量统计").build();
            writer.write(excelData, sheet);
        }
    }

    // 导出月度进出仓单
    public void exportMonthlyIo(HttpServletResponse response, int year, int month) throws IOException {
        List<MonthlyIoDTO> data = getMonthlyIoData(year, month);
        String fileName = String.format("软工230130号_月度进出仓单_%d年%02d月", year, month);
        setResponseHeader(response, fileName);
        EasyExcel.write(response.getOutputStream(), MonthlyIoDTO.class)
                .sheet("进出仓明细")
                .doWrite(data);
    }

    // 导出仓库账本（带物料信息头）
    public void exportLedger(HttpServletResponse response, int year, String materialCode) throws IOException {
        // 从数据库查询物料信息
        Material material = materialMapper.selectOne(
                new LambdaQueryWrapper<Material>().eq(Material::getCode, materialCode)
        );
        String materialName = material != null ? material.getName() : "未知物料";
        String spec = material != null ? material.getSpec() : "";
        String unit = material != null ? material.getUnit() : "";

        List<LedgerDTO> data = getLedgerData(year, materialCode);
        String fileName = String.format("软工230130号_仓库账本_%s_%d年", materialName, year);
        setResponseHeader(response, fileName);

        try (var excelWriter = EasyExcel.write(response.getOutputStream())// 无模板
                .build()) {

            // 第一个 sheet：写物料信息 + 表头
            WriteSheet sheet = EasyExcel.writerSheet("仓库账本").build();

            // 手动写入标题和物料信息（作为字符串列表）
            List<List<String>> head = new ArrayList<>();
            head.add(List.of("仓库物料账本"));
            head.add(List.of("物料编码：" + materialCode, "", "物料名称：" + materialName));
            head.add(List.of("规格型号：" + spec, "", "计量单位：" + unit));
            head.add(List.of()); // 空行
            head.add(List.of("日期", "进仓数量", "出仓数量", "当日结存"));

            excelWriter.write(head, sheet);

            // 写数据（转换为 List<List<String>>）
            List<List<String>> body = new ArrayList<>();
            for (LedgerDTO dto : data) {
                body.add(List.of(
                        dto.getDate(),
                        String.valueOf(dto.getInQty()),
                        String.valueOf(dto.getOutQty()),
                        String.valueOf(dto.getBalance())
                ));
            }
            excelWriter.write(body, sheet);
        }
    }
}
