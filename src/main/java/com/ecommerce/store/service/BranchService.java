package com.ecommerce.store.service;


import com.ecommerce.store.model.Branch;
import com.ecommerce.store.model.dto.BranchDTO;
import com.ecommerce.store.model.mapper.BaseMapper;
import com.ecommerce.store.service.impl.BaseServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public abstract class BranchService extends BaseServiceImpl<Branch, BranchDTO, Long> {

    protected BranchService(JpaRepository<Branch, Long> repo, BaseMapper<Branch, BranchDTO> mapper,
                         QuerydslPredicateExecutor<Branch> queryDsl) {
        super(repo, mapper, queryDsl);
    }
}
