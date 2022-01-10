package com.payhere.accountbook.services.history.infrastructure;

import com.payhere.accountbook.services.history.domain.History;
import com.payhere.accountbook.services.history.domain.HistoryRepository;
import com.payhere.accountbook.services.history.domain.QHistory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static com.payhere.accountbook.services.history.domain.QHistory.history;

@Repository
@Transactional
@RequiredArgsConstructor
public class QuerydslHistoryRepository extends AbstractHistoryRepository implements HistoryRepository {
    @PersistenceContext private EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void save(History history) {
        entityManager.persist(history);
    }

    @Override
    public Optional<History> findById(long historyId) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(history)
                        .where(eqHistoryId(historyId))
                        .fetchFirst()
        );
    }
}
