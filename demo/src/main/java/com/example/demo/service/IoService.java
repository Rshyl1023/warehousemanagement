package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.dto.WarehouseRecordDTO;
import com.example.demo.mapper.IOHeaderMapper;
import com.example.demo.mapper.IoProcessMapper;
import com.example.demo.dto.IoBatchRequest;
import com.example.demo.dto.IoItemDTO;
import com.example.demo.dto.IoRequest;
import com.example.demo.dto.IoQueryDTO;
import com.example.demo.pojo.IOHeader;
import com.example.demo.pojo.IODetail;
import com.example.demo.mapper.IODetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IoService {

    @Autowired
    private IoProcessMapper ioProcessMapper;

    @Autowired
    private IOHeaderMapper ioHeaderMapper;

    @Autowired
    private IODetailMapper ioDetailMapper;

    public List<WarehouseRecordDTO> getWarehouseRecords() {
        return ioDetailMapper.findWarehouseRecords();
    }
    
    // 生成单号：IO + 日期 + 6位序号（从数据库查询当天最大单号）
    public String generateIoNo() {
        String dateStr = LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = "IO" + dateStr;
        
        // 查询当天最大单号
        IOHeader latestHeader = ioHeaderMapper.selectOne(
                new LambdaQueryWrapper<IOHeader>()
                        .likeRight(IOHeader::getNo, prefix)
                        .orderByDesc(IOHeader::getNo)
                        .last("LIMIT 1")
        );
        
        int nextSeq = 1;
        if (latestHeader != null) {
            String latestNo = latestHeader.getNo();
            // 提取后6位序号
            String seqStr = latestNo.substring(prefix.length());
            nextSeq = Integer.parseInt(seqStr) + 1;
        }
        
        return prefix + String.format("%06d", nextSeq);
    }

    /**
     * 进出仓处理
     * rollbackFor = Exception.class
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> processIo(IoRequest request) {
        // 参数校验
        if (!"IN".equals(request.getType()) && !"OUT".equals(request.getType())) {
            return Map.of("success", false, "message", "类型必须是 IN 或 OUT");
        }
        if (request.getQty() == null || request.getQty() <= 0) {
            return Map.of("success", false, "message", "数量必须大于0");
        }

        String ioNo = generateIoNo();

        Map<String, Object> params = new HashMap<>();
        params.put("ioNo", ioNo);
        params.put("date", java.sql.Date.valueOf(LocalDate.now()));
        params.put("type", request.getType()); // 传递给存储过程，由存储过程处理
        params.put("operatorCode", request.getOperatorCode());
        params.put("handlerCode", request.getHandlerCode());
        params.put("remark", request.getRemark());
        params.put("materialCode", request.getMaterialCode());
        params.put("qty", request.getQty());
        params.put("resultCode", null);
        params.put("message", null);

        try {
            ioProcessMapper.callIoProcess(params);
        } catch (Exception e) {
            return Map.of("success", false, "message", "调用存储过程失败: " + e.getMessage());
        }

        Integer resultCode = (Integer) params.get("resultCode");
        String message = (String) params.get("message");

        if (resultCode != null && resultCode == 0) {
            return Map.of(
                    "success", true,
                    "message", message,
                    "ioNo", ioNo
            );
        } else {
            return Map.of(
                    "success", false,
                    "message", message != null ? message : "未知错误"
            );
        }
    }

    /**
     * 批量进出仓处理
     * rollbackFor = Exception.class
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> processBatchIo(IoBatchRequest request) {
        // 参数校验
        if (request.getItems() == null || request.getItems().isEmpty()) {
            return Map.of("success", false, "message", "物料明细不能为空");
        }

        // 生成统一的单号
        String ioNo = generateIoNo();
        
        List<Map<String, Object>> results = new ArrayList<>();
        int successCount = 0;
        int failCount = 0;

        // 逐个处理每个物料
        for (int i = 0; i < request.getItems().size(); i++) {
            IoItemDTO item = request.getItems().get(i);
            
            // 校验单个物料的参数
            if (!"IN".equals(item.getType()) && !"OUT".equals(item.getType())) {
                results.add(Map.of(
                    "index", i,
                    "materialCode", item.getMaterialCode(),
                    "success", false,
                    "message", "类型必须是 IN 或 OUT"
                ));
                failCount++;
                continue;
            }
            
            if (item.getQty() == null || item.getQty() <= 0) {
                results.add(Map.of(
                    "index", i,
                    "materialCode", item.getMaterialCode(),
                    "success", false,
                    "message", "数量必须大于0"
                ));
                failCount++;
                continue;
            }

            // 调用存储过程
            Map<String, Object> params = new HashMap<>();
            params.put("ioNo", ioNo);
            params.put("date", java.sql.Date.valueOf(LocalDate.now()));
            params.put("type", item.getType());
            params.put("operatorCode", request.getOperatorCode());
            params.put("handlerCode", request.getHandlerCode());
            params.put("remark", request.getRemark());
            params.put("materialCode", item.getMaterialCode());
            params.put("qty", item.getQty());
            params.put("resultCode", null);
            params.put("message", null);

            try {
                ioProcessMapper.callIoProcess(params);
                
                Integer resultCode = (Integer) params.get("resultCode");
                String message = (String) params.get("message");

                if (resultCode != null && resultCode == 0) {
                    results.add(Map.of(
                        "index", i,
                        "materialCode", item.getMaterialCode(),
                        "type", item.getType(),
                        "qty", item.getQty(),
                        "success", true,
                        "message", message
                    ));
                    successCount++;
                } else {
                    results.add(Map.of(
                        "index", i,
                        "materialCode", item.getMaterialCode(),
                        "success", false,
                        "message", message != null ? message : "未知错误"
                    ));
                    failCount++;
                }
            } catch (Exception e) {
                results.add(Map.of(
                    "index", i,
                    "materialCode", item.getMaterialCode(),
                    "success", false,
                    "message", "处理异常: " + e.getMessage()
                ));
                failCount++;
            }
        }

        // 返回汇总结果
        return Map.of(
            "success", failCount == 0,
            "message", String.format("批量处理完成：成功%d条，失败%d条", successCount, failCount),
            "ioNo", ioNo,
            "totalCount", request.getItems().size(),
            "successCount", successCount,
            "failCount", failCount,
            "details", results
        );
    }

    /**
     * 查询进出仓单据
     * @param queryDTO 查询条件
     * @return 符合条件的单据列表
     */
    public List<Map<String, Object>> queryIoHeaders(IoQueryDTO queryDTO) {
        LambdaQueryWrapper<IOHeader> headerWrapper = new LambdaQueryWrapper<>();
        
        // 日期范围查询
        if (queryDTO.getStartDate() != null) {
            headerWrapper.ge(IOHeader::getDate, queryDTO.getStartDate());
        }
        if (queryDTO.getEndDate() != null) {
            headerWrapper.le(IOHeader::getDate, queryDTO.getEndDate());
        }
        
        // 操作人员查询
        if (queryDTO.getOperatorCode() != null && !queryDTO.getOperatorCode().isEmpty()) {
            headerWrapper.eq(IOHeader::getOperatorCode, queryDTO.getOperatorCode());
        }
        
        // 备注模糊查询
        if (queryDTO.getRemark() != null && !queryDTO.getRemark().isEmpty()) {
            headerWrapper.like(IOHeader::getRemark, queryDTO.getRemark());
        }
        
        // 查询符合条件的主表记录
        List<IOHeader> headers = ioHeaderMapper.selectList(headerWrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        
        // 遍历主表记录，查询对应的明细信息
        for (IOHeader header : headers) {
            LambdaQueryWrapper<IODetail> detailWrapper = new LambdaQueryWrapper<>();
            detailWrapper.eq(IODetail::getIoNo, header.getNo());
            
            // 物料代码查询
            if (queryDTO.getMaterialCode() != null && !queryDTO.getMaterialCode().isEmpty()) {
                detailWrapper.eq(IODetail::getMaterialCode, queryDTO.getMaterialCode());
            }
            
            // 进出仓类型查询
            if (queryDTO.getType() != null && !queryDTO.getType().isEmpty()) {
                detailWrapper.eq(IODetail::getType, queryDTO.getType());
            }
            
            List<IODetail> details = ioDetailMapper.selectList(detailWrapper);
            
            // 如果有明细记录，则添加到结果中
            if (!details.isEmpty()) {
                Map<String, Object> record = new HashMap<>();
                record.put("header", header);
                record.put("details", details);
                result.add(record);
            }
        }
        
        return result;
    }
}
