package com.payhere.accountbook.services.history.domain;

import java.util.Optional;

public interface HistoryRepository {
    void save(History history);
    Optional<History> findById(long historyId);
}
