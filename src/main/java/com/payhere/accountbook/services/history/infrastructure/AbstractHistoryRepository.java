package com.payhere.accountbook.services.history.infrastructure;

import com.payhere.accountbook.services.history.domain.Writer;
import com.querydsl.core.types.dsl.BooleanExpression;

import static com.payhere.accountbook.services.history.domain.QHistory.history;

abstract public class AbstractHistoryRepository {
    protected BooleanExpression eqHistoryId(long historyId){
        return history.id.eq(historyId);
    }
    protected BooleanExpression eqWriter(Writer writer){
        return history.writer().eq(writer);
    }
    protected BooleanExpression notDeleted(){
        return history.isDeleted.isFalse();
    }
}
