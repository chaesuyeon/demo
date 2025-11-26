package com.example.demo.model.service;

import com.example.demo.model.domain.Member;
import lombok.Data;

@Data
public class AddMemberRequest {

    private String name;
    private String email;
    private String password;
    private String age;
    private String mobile;
    private String address;

    public Member toEntity() { 
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .age(age)
                .mobile(mobile)
                .address(address)
                .build();
    }
}
