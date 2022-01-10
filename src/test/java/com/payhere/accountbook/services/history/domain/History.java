package com.payhere.accountbook.services.history.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class History {
    private long money;
    private String memo;
    private Writer writer;
    private LocalDateTime createDateTime;

    private History(long money, String memo, Writer writer) {
        this.money = money;
        this.memo = memo;
        this.writer = writer;
    }

    public static History of(long money, String memo, Writer writer) {
        return new History(money, memo, writer);
    }
}
