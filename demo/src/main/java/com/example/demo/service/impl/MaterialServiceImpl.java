package com.example.demo.service.impl;

import com.example.demo.mapper.MaterialMapper;
import com.example.demo.pojo.Material;
import com.example.demo.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public List<Material> getAllMaterials() {
        return materialMapper.getAllMaterials();
    }
}
