package com.payhere.accountbook.services.history.presentation;

import com.payhere.accountbook.services.history.domain.History;
import org.springframework.stereotype.Repository;

@Repository
public class StubHistoryRepository implements HistoryRepository{
    @Override
    public void save(History history) {

    }
}
