package com.payhere.accountbook.services.history.presentation;

import com.payhere.accountbook.services.history.domain.History;
import com.payhere.accountbook.services.history.domain.Writer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HistoryRecord {
    private long money;
    private String memo;
    private Writer writer;
    private LocalDateTime createDateTime;

    public static HistoryRecord mapFrom(History history){
        return new HistoryRecord(history.getMoney(), history.getMemo(), history.getWriter(), history.getCreateDateTime());
    }
}
