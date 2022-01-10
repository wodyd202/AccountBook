package com.payhere.accountbook.services.history.presentation;

import com.payhere.accountbook.services.history.domain.History;

public interface HistoryRepository {
    void save(History history);
}
