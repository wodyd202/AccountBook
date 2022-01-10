package com.payhere.accountbook.services.history.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payhere.accountbook.services.customer.domain.Customer;
import com.payhere.accountbook.services.customer.domain.CustomerRepository;
import com.payhere.accountbook.services.history.domain.History;
import com.payhere.accountbook.services.history.domain.HistoryRepository;
import com.payhere.accountbook.services.history.domain.Writer;
import com.payhere.accountbook.web.security.JwtTokenFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class HistoryController_Test extends HistoryIntegrationTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    @DisplayName("가계부는 고객만 등록할 수 있음")
    void notAccess_401() throws Exception {
        // when
        String token = "";
        RegisterHistoryRequest registerHistoryRequest = RegisterHistoryRequest.of(1000, "memo");
        assertRegisterHistory(token, registerHistoryRequest)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("가계부 등록시 금액이 0원 이하일 경우 400 에러")
    void invalidMoney_400() throws Exception {
        // when
        String token = obtainJwtToken("email");
        RegisterHistoryRequest registerHistoryRequest = RegisterHistoryRequest.of(0, "memo");
        assertRegisterHistory(token, registerHistoryRequest)

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("가계부 등록시 메모를 입력하지 않을 경우 400 에러")
    void emptyMemo_400() throws Exception {
        // when
        String token = obtainJwtToken("email");
        RegisterHistoryRequest registerHistoryRequest = RegisterHistoryRequest.of(1100, "");
        assertRegisterHistory(token, registerHistoryRequest)

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("가계부 등록")
    void registerHistory() throws Exception {
        // when
        String token = obtainJwtToken("email");
        RegisterHistoryRequest registerHistoryRequest = RegisterHistoryRequest.of(1100, "메모");
        assertRegisterHistory(token, registerHistoryRequest)

        // then
        .andExpect(status().isCreated());
    }

    private final String AUTHENTICATION = "Authentication";
    private final String REGISTER_HISTORY_URL = "/api/history";
    private ResultActions assertRegisterHistory(String token, RegisterHistoryRequest registerHistoryRequest) throws Exception {
        return mockMvc.perform(post(REGISTER_HISTORY_URL)
                .header(AUTHENTICATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerHistoryRequest)));
    }

    @Test
    @DisplayName("금액 수정")
    void updateMoney() throws Exception {
        // given
        History history = saveHistory(History.of(1000L, "메모", Writer.of("email")));

        // when
        String token = obtainJwtToken("email");
        long historyId = history.getId();
        UpdateHistoryRequest updateHistoryRequest = UpdateHistoryRequest.of(10000L, null);
        assertUpdateHistory(token, historyId, updateHistoryRequest)

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("내용 수정")
    void updateMemo() throws Exception {
        // given
        History history = saveHistory(History.of(1000L, "메모", Writer.of("email")));

        // when
        String token = obtainJwtToken("email");
        long historyId = history.getId();
        UpdateHistoryRequest updateHistoryRequest = UpdateHistoryRequest.of(null, "메모 수정");
        assertUpdateHistory(token, historyId, updateHistoryRequest)

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("값을 입력하지 않아 수정사항이 존재하지 않음")
    void noUpdateWhenEmpty() throws Exception {
        // when
        String token = obtainJwtToken("email");
        long historyId = 1;
        UpdateHistoryRequest updateHistoryRequest = UpdateHistoryRequest.of(null, null);
        assertUpdateHistory(token, historyId, updateHistoryRequest)

        // then
        .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("기존 값과 동일하여 수정사항이 존재하지 않음")
    void noUpdateWhenSame() throws Exception {
        // given
        History history = saveHistory(History.of(1000L, "메모", Writer.of("email")));

        // when
        String token = obtainJwtToken("email");
        long historyId = history.getId();
        UpdateHistoryRequest updateHistoryRequest = UpdateHistoryRequest.of(1000L, "메모");
        assertUpdateHistory(token, historyId, updateHistoryRequest)

        // then
        .andExpect(status().isNoContent());
    }

    private final String UPDATE_HISTORY_URL = "/api/history/{historyId}";
    private ResultActions assertUpdateHistory(String token, long historyId, UpdateHistoryRequest updateHistoryRequest) throws Exception {
        return mockMvc.perform(patch(UPDATE_HISTORY_URL, historyId)
                .header(AUTHENTICATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateHistoryRequest)));
    }

    @Test
    @DisplayName("가계부 삭제")
    void remove() throws Exception{
        // given
        History history = saveHistory(History.of(1000L, "메모", Writer.of("email")));

        // when
        String token = obtainJwtToken("email");
        long historyId = history.getId();
        assertRemoveHistory(token, historyId)

        // then
        .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("다른 회원의 가계부를 삭제할 수 없음")
    void noRemoveSomeoneElseHistory() throws Exception {
        // given
        History history = saveHistory(History.of(1000L, "메모", Writer.of("다른회원")));

        // when
        String token = obtainJwtToken("email");
        long historyId = history.getId();
        assertRemoveHistory(token, historyId)

        // then
        .andExpect(status().isBadRequest());
    }

    private final String REMOVE_HISTORY_URL = "/api/history/{historyId}";
    private ResultActions assertRemoveHistory(String token, long historyId) throws Exception {
        return mockMvc.perform(delete(REMOVE_HISTORY_URL, historyId)
                .header(AUTHENTICATION, token));
    }

    @Test
    @DisplayName("삭제된 가계부 복구")
    void restore() throws Exception {
        // given
        History removedHistory = History.of(1000L, "메모", Writer.of("email"));
        removedHistory.remove(Writer.of("email"));
        History history = saveHistory(removedHistory);

        // when
        String token = obtainJwtToken("email");
        long historyId = history.getId();
        assertRestoreHistory(token, historyId)

        // then
        .andExpect(status().isOk());
    }

    private final String RESTORE_HISTORY_URL = "/api/history/{historyId}/restore";
    private ResultActions assertRestoreHistory(String token, long historyId) throws Exception {
        return mockMvc.perform(post(RESTORE_HISTORY_URL, historyId)
                .header(AUTHENTICATION, token));
    }

}
