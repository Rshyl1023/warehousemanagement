package com.example.demo.service;

import com.example.demo.mapper.IoProcessMapper;
import com.example.demo.dto.IoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class IoService {

    @Autowired
    private IoProcessMapper ioProcessMapper;

    // 生成单号：IO + 日期 + 6位序号（简化版，实际需加锁）
    public String generateIoNo() {
        String dateStr = LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 简化：固定从 000001 开始（实际应查数据库最大值）
        return "IO" + dateStr + "000002";
    }

    public Map<String, Object> processIo(IoRequest request) {
        // 参数校验
        if (!"IN".equals(request.getType()) && !"OUT".equals(request.getType())) {
            return Map.of("success", false, "message", "类型必须是 IN 或 OUT");
        }
        if (request.getQty() == null || request.getQty() <= 0) {
            return Map.of("success", false, "message", "数量必须大于0");
        }

        String ioNo = generateIoNo(); // 实际项目建议用更健壮的生成逻辑

        Map<String, Object> params = new HashMap<>();
        params.put("ioNo", ioNo);
        params.put("date", java.sql.Date.valueOf(LocalDate.now()));
        params.put("type", request.getType());
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
}
