package com.payhere.accountbook.services.history.presentation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterHistory {
    private long money;
    private String memo;

    public static RegisterHistory of(long money, String memo) {
        return new RegisterHistory(money, memo);
    }
}
