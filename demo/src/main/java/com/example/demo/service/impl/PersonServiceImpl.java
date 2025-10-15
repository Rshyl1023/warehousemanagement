package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dto.PersonAddDTO;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.pojo.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {
    @Autowired
    private PersonMapper mapper;

    @Override
    public boolean add(PersonAddDTO personAddDTO) {
        Person person = new Person();

        // 1. 手动属性映射（DTO → Entity）
//        person.setCode(personAddDTO.getC()); // 如果 code 由前端传入（如班级+座号），请确保 DTO 中有该字段
        person.setName(personAddDTO.getName());
        person.setGender(personAddDTO.getGender());
        person.setBirthday(personAddDTO.getBirthday());
        person.setIdCard(personAddDTO.getIdCard());
        person.setNativePlace(personAddDTO.getNativePlace()); // 注意：不是 setPNative()
        person.setAddress(personAddDTO.getAddress());
        person.setPhone(personAddDTO.getPhone());
        person.setOther(personAddDTO.getOther());

        // 2. 密码加密（推荐使用 BCrypt，而非 MD5）
//        String rawPassword = personAddDTO.getPassword();
//        String hashedPassword = DigestUtils.md5DigestAsHex(rawPassword.getBytes(StandardCharsets.UTF_8));
        // 更安全的做法（推荐）：
        // String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        // 暂时不加密
        String hashedPassword = personAddDTO.getPassword();
        person.setPasswordHash(hashedPassword);

        // 3. 设置系统字段
        person.setIsActive(true); // 默认激活

        // 4. 保存到数据库
        return this.save(person);
    }

    @Override
    public List<Person> getAll() {
        return mapper.findAll();
    }

    @Override
    public Person getById(String id) {
        return mapper.findById(id);
    }

    @Override
    public boolean update(Person person) {
        return update(person, Wrappers.lambdaUpdate(Person.class)
                .eq(Person::getCode, person.getCode()));
    }

    @Override
    public int delete(String id) {
        return mapper.delete(id);
    }
}