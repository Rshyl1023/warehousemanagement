package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.mapper.ResourceMapper;
import com.example.demo.pojo.*;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AuthServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements AuthService {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    // 1. 登录（明文验证）
    @Override
    public String login(String code, String password) {
        if (!StringUtils.hasText(code) || !StringUtils.hasText(password)) {
            return null;
        }

        Person person = personMapper.selectById(code);
        if (person == null || !person.getIsActive()) {
            return null;
        }

        // 明文比对（后续可替换为 BCrypt.matches）
        if (password.equals(person.getPasswordHash())) {
            // 临时：用 UUID 当作 token（你可存入 other 字段，但这里只返回 code 作凭证）
            return person.getCode(); // 临时返回 code 作为登录凭证
        }
        return null;
    }

    // 2. 查询用户有权访问的资源
    @Override
    public List<Resource> getUserResources(String personCode) {
        return resourceMapper.selectList(
                new LambdaQueryWrapper<Resource>()
                        .inSql(Resource::getCode,
                                "SELECT resource_code FROM permission WHERE person_code = '" + personCode + "' AND has_permission = true")
        );
    }

    // 3. 授予或收回权限
    @Override
    public boolean grantPermission(String personCode, String resourceCode, boolean hasPermission) {
        // 先查是否存在
        Permission perm = permissionMapper.selectOne(
                new LambdaQueryWrapper<Permission>()
                        .eq(Permission::getPersonCode, personCode)
                        .eq(Permission::getResourceCode, resourceCode)
        );

        if (perm == null) {
            // 新增
            perm = new Permission();
            perm.setPersonCode(personCode);
            perm.setResourceCode(resourceCode);
            perm.setHasPermission(hasPermission);
            return permissionMapper.insert(perm) > 0;
        } else {
            // 更新
            return permissionMapper.update(
                    null,
                    new LambdaUpdateWrapper<Permission>()
                            .eq(Permission::getPersonCode, personCode)
                            .eq(Permission::getResourceCode, resourceCode)
                            .set(Permission::getHasPermission, hasPermission)
            ) > 0;
        }
    }

    // 4. 添加资源（code 需要手动生成）
    @Override
    public boolean addResource(String name, String type) {
        if (!StringUtils.hasText(name)) return false;

        Resource res = new Resource();
        // 生成资源代码
        String code = "RES_" + System.currentTimeMillis();
        res.setCode(code);
        res.setName(name);
        res.setType(type != null ? type : "MENU");
        return resourceMapper.insert(res) > 0;
    }
}
