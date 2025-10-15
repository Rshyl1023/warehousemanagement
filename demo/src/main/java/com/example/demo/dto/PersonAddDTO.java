package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PersonAddDTO {

    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotBlank(message = "性别不能为空")
    private String gender; // 建议配合枚举校验，如 "男"/"女"

    private LocalDate birthday;

    private String idCard;

    private String nativePlace; // 避免使用 Java 关键字 'native'

    private String address;

    private String phone;

    @NotBlank(message = "密码不能为空")
    private String password; // 明文密码，由 Service 层加密为 passwordHash

    private String other;

    // 说明：
    // - code（原 P_CODE）由系统生成或前端传入（如班级+座号），若需用户输入可加字段
    // - passwordHash、isActive 等由后端自动处理，不在此接收
}