package com.ecommerce.store.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private List<ProductOrder> productOrders;

    private String fullname;

    private String address;

    private Long phoneNumber;

    private String email;

    @Getter
    @Setter
    public static class ProductOrder {
        private Long productId;
        private int quantity;
    }

}

