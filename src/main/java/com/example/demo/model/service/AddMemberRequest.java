package com.example.demo.model.service;

import com.example.demo.model.domain.Member;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddMemberRequest {

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름에는 특수문자를 사용할 수 없습니다.")
    private String name;

    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(regexp = "^(?=.*[A-Z]).{8,}$",
            message = "비밀번호는 8자리 이상이며 대문자를 1개 이상 포함해야 합니다.")
    private String password;

    @NotNull(message = "나이는 공백일 수 없습니다.")
    @Min(value = 19, message = "나이는 19세 이상이어야 합니다.")
    @Max(value = 90, message = "나이는 90세 이하여야 합니다.")
    private Integer age;

    @NotBlank(message = "전화번호는 공백일 수 없습니다.")
    private String mobile;

    @NotBlank(message = "주소는 공백일 수 없습니다.")
    private String address;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .age(String.valueOf(age)) // Member 엔티티가 age를 String으로 저장하므로 변환
                .mobile(mobile)
                .address(address)
                .build();
    }
}
