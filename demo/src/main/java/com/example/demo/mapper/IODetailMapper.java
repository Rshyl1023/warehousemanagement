package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.dto.WarehouseRecordDTO;
import com.example.demo.pojo.IODetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IODetailMapper extends BaseMapper<IODetail> {

    @Select("SELECT " +
            "h.no AS ioNo, " +
            "d.material_code AS materialCode, " +
            "m.name AS materialName, " +
            "m.spec AS spec, " +
            "m.unit AS unit, " +
            "d.type AS type, " +
            "d.qty AS qty, " +
            "p_operator.name AS operatorName, " +
            "p_handler.name AS handlerName, " +
            "h.date AS date " +
            "FROM io_detail d " +
            "JOIN io_header h ON d.io_no = h.no " +
            "JOIN material m ON d.material_code = m.code " +
            "JOIN person p_operator ON h.operator_code = p_operator.code " +
            "JOIN person p_handler ON h.handler_code = p_handler.code")
    List<WarehouseRecordDTO> findWarehouseRecords();
}
