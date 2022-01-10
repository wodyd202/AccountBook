package com.payhere.accountbook.services.history.presentation;

import com.payhere.accountbook.services.history.domain.History;
import com.payhere.accountbook.services.history.domain.Writer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class HistorySearchController_Test extends HistoryIntegrationTest {
    @Autowired MockMvc mockMvc;

    private final String AUTHENTICATION = "Authentication";

    @Test
    @DisplayName("가계부 상세 내역 조회")
    void getHistory() throws Exception {
        // given
        History history = saveHistory(History.of(3000, "memo", Writer.of("email")));

        // when
        String token = obtainJwtToken("email");
        long historyId = history.getId();
        mockMvc.perform(get("/api/history/{historyId}", historyId)
                        .header(AUTHENTICATION, token))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("가계부 리스트 조회")
    void getHistorys() throws Exception {
        // given
        saveHistory(History.of(3000, "memo", Writer.of("email")));

        // when
        String token = obtainJwtToken("email");
        mockMvc.perform(get("/api/history")
                        .header(AUTHENTICATION, token))

        // then
        .andExpect(status().isOk());
    }
}
