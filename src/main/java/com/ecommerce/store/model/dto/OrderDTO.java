package com.ecommerce.store.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderDTO extends BaseDTO<Long> {

    private List<ProductDTO> products;

    private String fullname;

    private String address;

    private Long phoneNumber;

    private String email;

    private BigDecimal totalPrice;
}
