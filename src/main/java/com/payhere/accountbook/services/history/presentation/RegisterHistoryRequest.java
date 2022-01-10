package com.payhere.accountbook.services.history.presentation;

import com.payhere.accountbook.services.history.application.RegisterHistory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterHistoryRequest {
    @Min(value = 1, message = "금액은 1원 이상으로 입력해주세요.")
    private long money;

    @NotBlank(message = "메모를 입력해주세요.")
    private String memo;

    public static RegisterHistoryRequest of(long money, String memo) {
        return new RegisterHistoryRequest(money, memo);
    }

    public RegisterHistory toRegisterHistory() {
        return RegisterHistory.of(money, memo);
    }
}
