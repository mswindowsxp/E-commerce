package com.ecommerce.store.service;


import com.ecommerce.store.model.Quantity;
import com.ecommerce.store.model.dto.QuantityDTO;
import com.ecommerce.store.model.mapper.QuantityMapper;
import com.ecommerce.store.reposirtory.QuantityRepository;
import com.ecommerce.store.service.impl.BaseServiceImpl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public abstract class QuantityService extends BaseServiceImpl<Quantity, QuantityDTO, Long> {
    public QuantityService(QuantityRepository repo, QuantityMapper mapper, QuerydslPredicateExecutor<Quantity> queryDsl) {
        super(repo, mapper, queryDsl);
    }

    public abstract void save(Quantity quantity);
}
