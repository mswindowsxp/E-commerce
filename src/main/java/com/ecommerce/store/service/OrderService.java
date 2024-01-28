package com.ecommerce.store.service;

import com.ecommerce.store.model.OrderDetail;
import com.ecommerce.store.model.dto.OrderDTO;
import com.ecommerce.store.model.mapper.BaseMapper;
import com.ecommerce.store.model.request.OrderRequest;
import com.ecommerce.store.model.response.ResponseModel;
import com.ecommerce.store.service.impl.BaseServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.security.core.Authentication;

public abstract class OrderService extends BaseServiceImpl<OrderDetail, OrderDTO, Long> {
    protected OrderService(JpaRepository<OrderDetail, Long> repo, BaseMapper<OrderDetail, OrderDTO> mapper, QuerydslPredicateExecutor<OrderDetail> queryDsl) {
        super(repo, mapper, queryDsl);
    }

    public abstract ResponseModel<String> order(OrderRequest orderRequest, Authentication authentication) throws Exception;
}
