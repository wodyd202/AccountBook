package com.payhere.accountbook.services.history.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class History_Test {

    @Test
    @DisplayName("가계부 생성")
    void newHistory(){
        // when
        History history = History.of(30000, "memo", Writer.of("작성자"));

        // then
        assertEquals(history.getMemo(), "memo");
        assertEquals(history.getMoney(), 30000);
        assertEquals(history.getWriter(), Writer.of("작성자"));
    }

}
