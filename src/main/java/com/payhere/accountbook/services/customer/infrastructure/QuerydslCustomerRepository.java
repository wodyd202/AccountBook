package com.payhere.accountbook.services.customer.infrastructure;

import com.payhere.accountbook.services.customer.domain.Customer;
import com.payhere.accountbook.services.customer.domain.CustomerRepository;
import com.payhere.accountbook.services.customer.domain.QCustomer;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static com.payhere.accountbook.services.customer.domain.QCustomer.customer;

@Repository
@Transactional
@RequiredArgsConstructor
public class QuerydslCustomerRepository implements CustomerRepository {
    @PersistenceContext private EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void save(Customer customer) {
        entityManager.persist(customer);
    }

    @Override
    public boolean existByEmail(String email) {
        return jpaQueryFactory.selectOne()
                .from(customer)
                .where(eqEmail(email))
                .fetchFirst() != null;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(customer)
                        .where(eqEmail(email))
                        .fetchFirst()
        );
    }

    private BooleanExpression eqEmail(String email){
        return customer.email.eq(email);
    }
}
