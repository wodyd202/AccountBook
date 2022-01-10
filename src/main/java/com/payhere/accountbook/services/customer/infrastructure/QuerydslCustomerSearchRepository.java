package com.payhere.accountbook.services.customer.infrastructure;

import com.payhere.accountbook.services.customer.application.CustomerRecord;
import com.payhere.accountbook.services.customer.application.CustomerSearchRepository;
import com.payhere.accountbook.services.customer.domain.QCustomer;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.payhere.accountbook.services.customer.domain.QCustomer.customer;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuerydslCustomerSearchRepository implements CustomerSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<CustomerRecord> findByEmail(String email) {
        return Optional.ofNullable(
                jpaQueryFactory.select(Projections.constructor(CustomerRecord.class,
                                customer.email,
                                customer.password
                            ))
                        .from(customer)
                        .where(eqEmail(email))
                        .fetchFirst()
        );
    }

    private BooleanExpression eqEmail(String email){
        return customer.email.eq(email);
    }
}
