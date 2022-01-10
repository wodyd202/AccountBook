package com.payhere.accountbook.services.history.application;

import com.payhere.accountbook.services.history.domain.History;
import com.payhere.accountbook.services.history.domain.Writer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HistoryRecord {
    private long id;
    private long money;
    private String memo;
    private Writer writer;
    private LocalDateTime createDateTime;

    public static HistoryRecord mapFrom(History history){
        return new HistoryRecord(history.getId(), history.getMoney(), history.getMemo(), history.getWriter(), history.getCreateDateTime());
    }
}
