package com.ecommerce.store.reposirtory;

import com.ecommerce.store.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetail, Long>, QuerydslPredicateExecutor<OrderDetail> {
}
