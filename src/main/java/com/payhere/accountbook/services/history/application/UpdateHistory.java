package com.payhere.accountbook.services.history.application;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateHistory {
    private Long money;
    private String memo;

    public static UpdateHistory of(Long money, String memo) {
        return new UpdateHistory(money, memo);
    }
}
