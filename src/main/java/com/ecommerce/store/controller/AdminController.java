package com.ecommerce.store.controller;

import com.ecommerce.store.common.ApiConstant;
import com.ecommerce.store.model.MetricSearch;
import com.ecommerce.store.model.dto.ProductDTO;
import com.ecommerce.store.model.predicate.OrderPredicate;
import com.ecommerce.store.model.response.ResponseModel;
import com.ecommerce.store.service.OrderService;
import com.ecommerce.store.service.ProductService;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstant.ADMIN)
@RequiredArgsConstructor
@Tag(name = "Product Management")
public class AdminController {

    private final ProductService productService;
    private final OrderPredicate orderPredicate;
    private final OrderService orderService;

    @Operation(description = "Add new Product")
    @PostMapping(ApiConstant.PRODUCT + ApiConstant.ADD)
    public ResponseModel<?> addNewProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @Operation(description = "Update exists product")
    @PutMapping(ApiConstant.PRODUCT + ApiConstant.UPDATE)
    public ResponseModel<?> updateProduct(@RequestBody ProductDTO productDTO) {
        return productService.update(productDTO);
    }

    @Operation(description = "Delete exists product")
    @DeleteMapping(ApiConstant.PRODUCT + ApiConstant.DELETE + "/{productId}")
    public ResponseModel<?> deleteProduct(@PathVariable("productId") Long productId) {
        return productService.deleteById(productId);
    }

    @Operation(description = "Update a quantity of product")
    @PutMapping(ApiConstant.PRODUCT + ApiConstant.UPDATE + ApiConstant.QUANTITY)
    public ResponseModel<?> updateProductQuantity(@RequestParam Long productId, @RequestParam Long quantity) {
        return productService.updateQuantity(productId, quantity);
    }

    @Operation(description = "List all the order by paging and search condition")
    @PostMapping(ApiConstant.ORDER + ApiConstant.LIST)
    public ResponseModel<?> getListOrder(@RequestBody(required = false) MetricSearch metricSearch) {
        Predicate predicate = orderPredicate.findByMetricFilter(metricSearch);
        return orderService.findByPredicate(metricSearch, predicate);
    }
}
