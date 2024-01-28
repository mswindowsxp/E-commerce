package com.ecommerce.store.reposirtory;

import com.ecommerce.store.model.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityRepository extends JpaRepository<Quantity, Long>, QuerydslPredicateExecutor<Quantity> {
}
