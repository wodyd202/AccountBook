package com.payhere.accountbook.services.history.infrastructure;

import com.payhere.accountbook.services.history.domain.History;
import com.payhere.accountbook.services.history.domain.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
@RequiredArgsConstructor
public class QuerydslHistoryRepository implements HistoryRepository {
    @PersistenceContext private EntityManager entityManager;

    @Override
    public void save(History history) {
        entityManager.persist(history);
    }
}
