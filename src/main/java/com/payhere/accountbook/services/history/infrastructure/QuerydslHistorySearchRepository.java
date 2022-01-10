package com.payhere.accountbook.services.history.infrastructure;

import com.payhere.accountbook.services.history.application.HistoryRecord;
import com.payhere.accountbook.services.history.application.HistorySearchRepository;
import com.payhere.accountbook.services.history.domain.QHistory;
import com.payhere.accountbook.services.history.domain.Writer;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.payhere.accountbook.services.history.domain.QHistory.history;
import static com.querydsl.core.types.dsl.Expressions.asSimple;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuerydslHistorySearchRepository extends AbstractHistoryRepository implements HistorySearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<HistoryRecord> getHistory(long historyId, Writer writer) {
        return Optional.ofNullable(
                jpaQueryFactory.select(Projections.constructor(HistoryRecord.class,
                                asSimple(historyId),
                                history.money,
                                history.memo,
                                asSimple(writer),
                                history.createDateTime
                            ))
                        .from(history)
                        .where(eqHistoryId(historyId), eqWriter(writer), notDeleted())
                        .fetchFirst()
        );
    }

    @Override
    public List<HistoryRecord> getHistorys(Writer writer) {
        return jpaQueryFactory.select(Projections.constructor(HistoryRecord.class,
                        history.id,
                        history.money,
                        history.memo,
                        asSimple(writer),
                        history.createDateTime
                ))
                .from(history)
                .where(eqWriter(writer), notDeleted())
                .fetch();
    }

}
