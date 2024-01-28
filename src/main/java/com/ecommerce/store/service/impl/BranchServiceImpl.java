package com.ecommerce.store.service.impl;

import com.ecommerce.store.model.Branch;
import com.ecommerce.store.model.mapper.BranchMapper;
import com.ecommerce.store.reposirtory.BranchRepository;
import com.ecommerce.store.service.BranchService;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Service;

@Service
public class BranchServiceImpl extends BranchService {

    public BranchServiceImpl(BranchRepository repo, BranchMapper mapper,
                             QuerydslPredicateExecutor<Branch> queryDsl) {
        super(repo, mapper, queryDsl);
    }
}
