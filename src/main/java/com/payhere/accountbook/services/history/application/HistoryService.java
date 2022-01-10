package com.payhere.accountbook.services.history.application;

import com.payhere.accountbook.services.history.domain.History;
import com.payhere.accountbook.services.history.domain.HistoryRepository;
import com.payhere.accountbook.services.history.domain.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryRecord register(RegisterHistory registerHistory, Writer writer) {
        History history = History.of(registerHistory.getMoney(), registerHistory.getMemo(), writer);
        historyRepository.save(history);
        return HistoryRecord.mapFrom(history);
    }

    public HistoryRecord update(long historyId, UpdateHistory updateHistory, Writer updater) {
        History history = getHistory(historyId);

        boolean isUpdateMemo = false;
        boolean isUpdateMoney = false;
        if(StringUtils.hasText(updateHistory.getMemo())){
            isUpdateMemo = history.updateMemo(updater, updateHistory.getMemo());
        }

        if(updateHistory.getMoney() != null){
            isUpdateMoney = history.updateMoney(updater, updateHistory.getMoney());
        }

        verifyUpdatedHistory(isUpdateMemo, isUpdateMoney);

        return HistoryRecord.mapFrom(history);
    }

    private void verifyUpdatedHistory(boolean isUpdateMemo, boolean isUpdateMoney) {
        if(noUpdate(isUpdateMemo, isUpdateMoney)){
            throw new NoUpdateHistoryException();
        }
    }

    private boolean noUpdate(boolean isUpdateMemo, boolean isUpdateMoney) {
        return !isUpdateMemo && !isUpdateMoney;
    }

    public void remove(long historyId, Writer remover) {
        History history = getHistory(historyId);

        history.remove(remover);
    }

    public HistoryRecord restore(long historyId, Writer writer) {
        History history = getHistory(historyId);

        history.restore(writer);
        return HistoryRecord.mapFrom(history);
    }

    private History getHistory(long historyId) {
        return historyRepository.findById(historyId).orElseThrow(HistoryNotFoundException::new);
    }

    // query
    private final HistorySearchRepository historySearchRepository;

    public HistoryRecord getHistory(long historyId, Writer writer){
        return historySearchRepository.getHistory(historyId, writer).orElseThrow(HistoryNotFoundException::new);
    }

    public List<HistoryRecord> getHistorys(Writer writer) {
        return historySearchRepository.getHistorys(writer);
    }
}
