package com.example.demo;

import com.example.demo.dto.IoQueryDTO;
import com.example.demo.service.IoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class IoServiceTest {

    @Autowired
    private IoService ioService;

    @Test
    public void testQueryIoHeaders() {
        IoQueryDTO queryDTO = new IoQueryDTO();
        // 测试日期范围查询
        queryDTO.setStartDate(LocalDate.of(2023, 1, 1));
        queryDTO.setEndDate(LocalDate.of(2023, 12, 31));
        
        List<Map<String, Object>> result = ioService.queryIoHeaders(queryDTO);
        System.out.println("日期范围查询结果数量: " + result.size());
    }
    
    @Test
    public void testQueryByMaterialCode() {
        IoQueryDTO queryDTO = new IoQueryDTO();
        // 测试物料代码查询
        queryDTO.setMaterialCode("M001");
        
        List<Map<String, Object>> result = ioService.queryIoHeaders(queryDTO);
        System.out.println("物料代码查询结果数量: " + result.size());
    }
    
    @Test
    public void testQueryByOperatorCode() {
        IoQueryDTO queryDTO = new IoQueryDTO();
        // 测试操作人员查询
        queryDTO.setOperatorCode("P001");
        
        List<Map<String, Object>> result = ioService.queryIoHeaders(queryDTO);
        System.out.println("操作人员查询结果数量: " + result.size());
    }
    
    @Test
    public void testQueryByRemark() {
        IoQueryDTO queryDTO = new IoQueryDTO();
        // 测试备注模糊查询
        queryDTO.setRemark("测试");
        
        List<Map<String, Object>> result = ioService.queryIoHeaders(queryDTO);
        System.out.println("备注查询结果数量: " + result.size());
    }
    
    @Test
    public void testQueryByType() {
        IoQueryDTO queryDTO = new IoQueryDTO();
        // 测试进出仓类型查询
        queryDTO.setType("IN");
        
        List<Map<String, Object>> result = ioService.queryIoHeaders(queryDTO);
        System.out.println("进出仓类型(IN)查询结果数量: " + result.size());
        
        // 测试另一种类型
        queryDTO.setType("OUT");
        result = ioService.queryIoHeaders(queryDTO);
        System.out.println("进出仓类型(OUT)查询结果数量: " + result.size());
    }
    
    @Test
    public void testCombinedQuery() {
        IoQueryDTO queryDTO = new IoQueryDTO();
        // 测试组合条件查询
        queryDTO.setStartDate(LocalDate.of(2023, 1, 1));
        queryDTO.setEndDate(LocalDate.of(2023, 12, 31));
        queryDTO.setMaterialCode("M001");
        queryDTO.setOperatorCode("P001");
        queryDTO.setRemark("测试");
        queryDTO.setType("IN");
        
        List<Map<String, Object>> result = ioService.queryIoHeaders(queryDTO);
        System.out.println("组合条件查询结果数量: " + result.size());
    }
}