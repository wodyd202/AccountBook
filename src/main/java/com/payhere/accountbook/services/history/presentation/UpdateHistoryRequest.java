package com.payhere.accountbook.services.history.presentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.payhere.accountbook.services.history.application.UpdateHistory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateHistoryRequest {
    @Min(value = 1, message = "금액은 1원 이상으로 입력해주세요.")
    private Long money;
    private String memo;

    public static UpdateHistoryRequest of(Long money, String memo) {
        return new UpdateHistoryRequest(money, memo);
    }

    public UpdateHistory toUpdateHistory() {
        return UpdateHistory.of(money, memo);
    }

    @JsonIgnore
    public boolean isEmpty() {
        return money == null &&
                !StringUtils.hasText(memo);
    }
}
