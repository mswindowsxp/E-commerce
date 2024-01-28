package com.ecommerce.store.controller;

import com.ecommerce.store.common.ApiConstant;
import com.ecommerce.store.model.MetricSearch;
import com.ecommerce.store.model.predicate.ProductPredicate;
import com.ecommerce.store.model.request.OrderRequest;
import com.ecommerce.store.model.response.ResponseModel;
import com.ecommerce.store.service.OrderService;
import com.ecommerce.store.service.ProductService;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ApiConstant.CUSTOMER)
@Tag(name = "Customer Controller")
@RequiredArgsConstructor
public class CustomerController {

    private final ProductService productService;
    private final ProductPredicate productPredicate;
    private final OrderService orderService;

    @Operation(description = "List all product by paging and search condition")
    @PostMapping(ApiConstant.PRODUCT + ApiConstant.LIST)
    public ResponseModel<?> getAllProductDetail(@RequestBody(required = false) MetricSearch metricSearch) {
        Predicate predicate = productPredicate.findByMetricFilter(metricSearch);
        return productService.findByPredicate(metricSearch, predicate);
    }

    @Operation(description = "Product Detail")
    @GetMapping(ApiConstant.PRODUCT)
    public ResponseModel<?> getProductDetail(@RequestParam("id") Long productId) {
        return productService.getById(productId);
    }

    @Operation(description = "Do order a product(s)")
    @PostMapping(ApiConstant.ORDER + ApiConstant.ADD)
    public ResponseModel<?> order(@Valid @RequestBody OrderRequest orderRequest, Authentication authentication) throws Exception {
        return orderService.order(orderRequest, authentication);
    }

    @Operation(description = "Order detail")
    @GetMapping(ApiConstant.ORDER)
    public ResponseModel<?> order(@RequestParam("orderId") Long id) {
        return orderService.getById(id);
    }
}
