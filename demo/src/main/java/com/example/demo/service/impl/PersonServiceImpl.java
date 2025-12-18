package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dto.PersonAddDTO;
import com.example.demo.mapper.IOHeaderMapper;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.pojo.IOHeader;
import com.example.demo.pojo.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {
    @Autowired
    private PersonMapper mapper;

    @Autowired
    private IOHeaderMapper ioHeaderMapper;

    private static final String ID_CARD_REGEX = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9X]$";
    private static final Pattern ID_CARD_PATTERN = Pattern.compile(ID_CARD_REGEX);

    @Override
    public boolean add(PersonAddDTO personAddDTO) {

        // 1. 身份证校验（非空 + 格式）
        String idCard = personAddDTO.getIdCard();
        if (idCard == null || idCard.isEmpty()) {
            return false; // 身份证不能为空
        }
        if (!ID_CARD_PATTERN.matcher(idCard).matches()) {
            return false; // 格式不合法
        }

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
    public List<Person> searchByNameKeyword(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return list(); // 返回全部（或可返回空列表）
        }

        // 使用 MyBatis-Plus 的 LambdaQueryWrapper 实现模糊查询
        return list(new LambdaQueryWrapper<Person>()
                .like(Person::getName, keyword.trim()));
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
        Person person = new Person();
        person.setCode(id);
        person.setIsActive(false);
        return mapper.updateById(person);
    }
}