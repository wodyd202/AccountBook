package com.payhere.accountbook.services.customer.presentation;

import com.payhere.accountbook.services.customer.application.SignUp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequest {
    @Email(message = "사용할 이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "사용할 이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "사용할 비밀번호를 입력해주세요.")
    private String password;

    public static SignUpRequest of(String email, String password) {
        return new SignUpRequest(email, password);
    }

    public SignUp toSignUp() {
        return SignUp.of(email, password);
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }
}
