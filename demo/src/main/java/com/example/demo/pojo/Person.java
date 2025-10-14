package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String P_CODE;
    private String P_NAME;
    private String P_GENDER;
    private LocalDate P_BIRTHDAY;
    private String P_ID_CARD;
    private String P_NATIVE;
    private String P_ADDRESS;
    private String P_PHONE;
    private String P_PASSWORD_HASH;
    private Boolean P_IS_ACTIVE;
    private String P_OTHER;
}