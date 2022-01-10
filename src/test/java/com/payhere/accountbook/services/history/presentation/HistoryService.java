package com.payhere.accountbook.services.history.presentation;

import com.payhere.accountbook.services.history.domain.History;
import com.payhere.accountbook.services.history.domain.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryRecord register(RegisterHistory registerHistory, Writer writer) {
        History history = History.of(registerHistory.getMoney(), registerHistory.getMemo(), writer);
        historyRepository.save(history);
        return HistoryRecord.mapFrom(history);
    }
}
