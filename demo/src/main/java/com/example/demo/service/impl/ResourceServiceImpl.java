package com.example.demo.service.impl;

import com.example.demo.mapper.ResourceMapper;
import com.example.demo.pojo.Resource;
import com.example.demo.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> getAllResources() {
        return resourceMapper.selectList(null);
    }
}
