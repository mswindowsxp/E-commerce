package com.ecommerce.store.service.impl;

import com.ecommerce.store.common.ResponseMessage;
import com.ecommerce.store.model.Branch;
import com.ecommerce.store.model.Product;
import com.ecommerce.store.model.Quantity;
import com.ecommerce.store.model.dto.ProductDTO;
import com.ecommerce.store.model.dto.QuantityDTO;
import com.ecommerce.store.model.mapper.ProductMapper;
import com.ecommerce.store.model.mapper.QuantityMapper;
import com.ecommerce.store.model.predicate.ProductPredicate;
import com.ecommerce.store.model.response.ResponseModel;
import com.ecommerce.store.reposirtory.BranchRepository;
import com.ecommerce.store.reposirtory.ProductRepository;
import com.ecommerce.store.service.ProductService;
import com.ecommerce.store.service.QuantityService;
import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl extends ProductService {

    private final BranchRepository branchRepository;
    private final QuantityService quantityService;
    private final ProductPredicate productPredicate;
    private final ProductRepository productRepository;
    private final QuantityMapper quantityMapper;

    public ProductServiceImpl(ProductRepository repository, ProductMapper productMapper, QuerydslPredicateExecutor<Product> queryDsl,
                              BranchRepository branchRepository, QuantityService quantityService,
                              ProductPredicate productPredicate, QuantityMapper quantityMapper) {
        super(repository, productMapper, queryDsl);
        this.branchRepository = branchRepository;
        this.quantityService = quantityService;
        this.productPredicate = productPredicate;
        this.productRepository = repository;
        this.quantityMapper = quantityMapper;
    }

    @Override
    public ResponseModel<String> addProduct(ProductDTO productDTO) {

        if (productDTO.getBranch() == null) {
            return ResponseModel.failure("Branch can not null");
        }
        Long branchId = productDTO.getBranch().getId();

        Optional<Branch> branch = branchRepository.findById(branchId);
        if (!branch.isPresent()) {
            return ResponseModel.failure(ResponseMessage.NOT_FOUND.getMessage());
        }
        Product product = mapper.toEntity(productDTO);
        product.setBranch(branch.get());

        if (productDTO.getQuantityDTO() != null) {
            QuantityDTO quantityDTO = new QuantityDTO();
            Long qtt = productDTO.getQuantityDTO().getQuantity();
            if (qtt == null || qtt.compareTo(0L) <= 0) {
                return ResponseModel.failure("Quantity must be more than 0");
            }
            quantityDTO.setQuantity(productDTO.getQuantityDTO().getQuantity());

            Quantity quantity = quantityMapper.toEntity(quantityDTO);
            product.setQuantity(quantity);
            quantity.setProduct(product);
        } else {
            return ResponseModel.failure("Quantity can not null");
        }
        productRepository.save(product);

        return ResponseModel.successful(ResponseMessage.SUCCESS.getMessage());
    }

    @Override
    public ResponseModel<String> updateQuantity(Long productId, Long quantity) {
        if (productId == null) {
            return ResponseModel.failure("Product can not null");
        }
        if (quantity == null || quantity.compareTo(0L) <= 0) {
            return ResponseModel.failure("Quantity can not less than 0");
        }
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            return ResponseModel.failure(ResponseMessage.NOT_FOUND.getMessage());
        }
        Product product = productOptional.get();
        Quantity qtt;
        if (product.getQuantity() == null) {
            qtt = new Quantity();
            qtt.setProduct(product);
        } else {
            qtt = product.getQuantity();
        }
        qtt.setQuantity(quantity);
        quantityService.save(qtt);

        product.setQuantity(qtt);
        productRepository.save(product);
        return ResponseModel.successful(ResponseMessage.SUCCESS.getMessage());
    }

    @Override
    public List<Product> findByIds(List<Long> ids) {

        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        Predicate predicate = productPredicate.findByProductIdIn(ids);
        return (List<Product>) productRepository.findAll(predicate);
    }

    @Override
    public void updateList(List<Product> products) {
        if (CollectionUtils.isEmpty(products)) {
            return;
        }
        productRepository.saveAll(products);
    }

}
