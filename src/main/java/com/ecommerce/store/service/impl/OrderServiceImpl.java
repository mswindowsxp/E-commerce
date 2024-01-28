package com.ecommerce.store.service.impl;

import com.ecommerce.store.common.ResponseMessage;
import com.ecommerce.store.model.OrderDetail;
import com.ecommerce.store.model.Product;
import com.ecommerce.store.model.mapper.OrderMapper;
import com.ecommerce.store.model.request.OrderRequest;
import com.ecommerce.store.model.response.ResponseModel;
import com.ecommerce.store.model.security.UserPrinciple;
import com.ecommerce.store.reposirtory.OrderRepository;
import com.ecommerce.store.service.OrderService;
import com.ecommerce.store.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends OrderService {

    private final ProductService productService;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository repo, OrderMapper mapper,
                            QuerydslPredicateExecutor<OrderDetail> queryDsl,
                            ProductService productService) {
        super(repo, mapper, queryDsl);
        this.productService = productService;
        this.orderRepository = repo;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ResponseModel<String> order(OrderRequest orderRequest, Authentication authentication) throws Exception {
        try {
            validOrderRequest(orderRequest, authentication);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModel.failure(e.getMessage());
        }

        List<OrderRequest.ProductOrder> productOrders = orderRequest.getProductOrders();
        List<Long> productsId = productOrders.stream().map(OrderRequest.ProductOrder::getProductId).collect(Collectors.toList());

        List<Product> products = productService.findByIds(productsId);
        List<Long> productOutOfStock = checkProductStock(products, productOrders);

        if (!productOutOfStock.isEmpty()) {
            String productOutOfStockStr = productOutOfStock.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("-", "{", "}"));
            return ResponseModel.failure("Product is out of stock: " + productOutOfStockStr);
        }
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal quantity;
        OrderRequest.ProductOrder productOrder;
        for (Product p : products) {
            BigDecimal pPrice = p.getPrice();
            Optional<OrderRequest.ProductOrder> orderOption = productOrders.stream().filter(x -> x.getProductId().equals(p.getId())).findFirst();
            if (orderOption.isPresent()) {
                productOrder = orderOption.get();
                quantity = new BigDecimal(productOrder.getQuantity());
                totalPrice = totalPrice.add(pPrice.multiply(quantity));

                // update product quantity
                p.getQuantity().setQuantity(p.getQuantity().getQuantity() - (long) productOrder.getQuantity());
            }
        }

        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setTotalPrice(totalPrice);
        orderDetail.setAddress(orderRequest.getAddress());
        orderDetail.setFullname(orderRequest.getFullname());
        orderDetail.setPhoneNumber(orderRequest.getPhoneNumber());
        orderDetail.setEmail(orderRequest.getEmail());
        orderDetail.setProducts(products);
        OrderDetail success = orderRepository.save(orderDetail);

        if (success.getId() == null) {
            throw new Exception(ResponseMessage.ERROR_SYSTEM.getMessage());
        } else {
            // save for update quantity
            productService.updateList(products);
        }

        return ResponseModel.successful(ResponseMessage.SUCCESS.getMessage() + " Order Id:" + success.getId());
    }

    private List<Long> checkProductStock(List<Product> products, List<OrderRequest.ProductOrder> productOrders) {
        List<Long> productOutOfStock = new ArrayList<>();
        for (Product p : products) {
            productOrders.stream().filter(x -> x.getProductId().equals(p.getId())).findFirst().ifPresent(x -> {
                if (p.getQuantity() == null || p.getQuantity().getQuantity() == null || p.getQuantity().getQuantity() < x.getQuantity()) {
                    productOutOfStock.add(p.getId());
                }
            });
        }
        return productOutOfStock;
    }

    private boolean validOrderDetail(List<OrderRequest.ProductOrder> productOrder) {
        if (productOrder.isEmpty()) {
            return false;
        }
        for (OrderRequest.ProductOrder po : productOrder) {
            if (po.getProductId() == null || po.getQuantity() <= 0) {
                return false;
            }
        }
        return true;
    }

    private void validOrderRequest(OrderRequest orderRequest, Authentication authentication) throws Exception {
        UserPrinciple userOAuth2 = null;
        if (authentication != null) {
            userOAuth2 = (UserPrinciple) authentication.getPrincipal();
        }

        if (orderRequest == null || orderRequest.getProductOrders().isEmpty()) {
            throw new Exception("Please select product.");
        }
        List<OrderRequest.ProductOrder> productOrders = orderRequest.getProductOrders();

        boolean isValidOrderDetail = validOrderDetail(productOrders);
        if (!isValidOrderDetail) {
            throw new Exception("Order detail is not valid.");
        }
        List<Long> productsId = productOrders.stream().map(OrderRequest.ProductOrder::getProductId).collect(Collectors.toList());

        if (productsId.isEmpty()) {
            throw new Exception("Please select product");
        }

        String fullName = orderRequest.getFullname();
        String email = orderRequest.getEmail();

        if (fullName == null) {
            if (userOAuth2 != null) {
                orderRequest.setFullname(userOAuth2.getFullName());
            } else {
                throw new Exception("Full name can not null");
            }
        }
        if (email == null) {
            if (userOAuth2 != null) {
                orderRequest.setEmail(userOAuth2.getEmail());
            } else {
                throw new Exception("Email can not null");
            }
        }

    }


}
