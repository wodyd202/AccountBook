package com.payhere.accountbook.services.history.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("금액 변경")
    void updateMoney(){
        // given
        History history = History.of(30000, "memo", Writer.of("작성자"));

        // when
        boolean isUpdate = history.updateMoney(Writer.of("작성자"), 2000);

        // then
        assertTrue(isUpdate);
        assertEquals(history.getMoney(), 2000);
    }

    @Test
    @DisplayName("금액이 변경되지 않음")
    void noUpdateMoney(){
        // given
        History history = History.of(30000, "memo", Writer.of("작성자"));

        // when
        boolean isUpdate = history.updateMoney(Writer.of("작성자"), 30000);

        // then
        assertFalse(isUpdate);
    }

    @Test
    @DisplayName("메모 변경")
    void updateMemo(){
        // given
        History history = History.of(30000, "memo", Writer.of("작성자"));

        // when
        boolean isUpdate = history.updateMemo(Writer.of("작성자"), "메모 변경");

        // then
        assertTrue(isUpdate);
        assertEquals(history.getMemo(), "메모 변경");
    }

    @Test
    @DisplayName("메모가 변경되지 않음")
    void noUpdateMemo(){
        // given
        History history = History.of(30000, "memo", Writer.of("작성자"));

        // when
        boolean isUpdate = history.updateMemo(Writer.of("작성자"), "memo");

        // then
        assertFalse(isUpdate);
    }

    @Test
    @DisplayName("다른 고객의 가계부를 수정할 수 없음")
    void noMemoUpdatePermissionException(){
        // given
        History history = History.of(30000, "memo", Writer.of("작성자"));

        // when
        assertThrows(NoUpdatePermissionException.class, ()->{
            history.updateMemo(Writer.of("다른 고객"), "memo");
        });
    }

    @Test
    @DisplayName("다른 고객의 가계부를 수정할 수 없음")
    void noMoneyUpdatePermissionException(){
        // given
        History history = History.of(30000, "memo", Writer.of("작성자"));

        // when
        assertThrows(NoUpdatePermissionException.class, ()->{
            history.updateMoney(Writer.of("다른 고객"), 30);
        });
    }

}
