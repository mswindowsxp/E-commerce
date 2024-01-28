package com.ecommerce.store.service;


import com.ecommerce.store.model.Product;
import com.ecommerce.store.model.dto.ProductDTO;
import com.ecommerce.store.model.mapper.ProductMapper;
import com.ecommerce.store.model.response.ResponseModel;
import com.ecommerce.store.reposirtory.ProductRepository;
import com.ecommerce.store.service.impl.BaseServiceImpl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public abstract class ProductService extends BaseServiceImpl<Product, ProductDTO, Long> {

    protected ProductService(ProductRepository repository, ProductMapper productMapper, QuerydslPredicateExecutor<Product> queryDsl) {
        super(repository, productMapper, queryDsl);
    }

    public abstract ResponseModel<String> addProduct(ProductDTO productDTO);

    public abstract ResponseModel<String> updateQuantity(Long productId, Long quantity);

    public abstract List<Product> findByIds(List<Long> ids);

    public abstract void updateList(List<Product> products);
}
