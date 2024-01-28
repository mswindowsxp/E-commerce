package com.ecommerce.store.service.impl;

import com.ecommerce.store.model.Quantity;
import com.ecommerce.store.model.mapper.QuantityMapper;
import com.ecommerce.store.reposirtory.QuantityRepository;
import com.ecommerce.store.service.QuantityService;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Service;

@Service
public class QuantityServiceImpl extends QuantityService {

    private final QuantityRepository quantityRepository;

    public QuantityServiceImpl(QuantityRepository repo, QuantityMapper mapper, QuerydslPredicateExecutor<Quantity> queryDsl) {
        super(repo, mapper, queryDsl);
        this.quantityRepository = repo;
    }

    @Override
    public void save(Quantity quantity) {
        if (quantity == null) {
            return;
        }
        quantityRepository.save(quantity);
    }
}
