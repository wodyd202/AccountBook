package com.payhere.accountbook.services.history.application;

import com.payhere.accountbook.services.history.domain.Writer;

import java.util.List;
import java.util.Optional;

public interface HistorySearchRepository {
    Optional<HistoryRecord> getHistory(long historyId, Writer writer);
    List<HistoryRecord> getHistorys(Writer writer);
}
