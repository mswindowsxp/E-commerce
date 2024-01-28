package com.ecommerce.store.model.dto;

import com.ecommerce.store.model.Branch;
import com.ecommerce.store.model.Color;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductDTO extends BaseDTO<Long> {

    private String name;

    private Color color;

    private BigDecimal price;

    private Branch branch;

    private QuantityDTO quantityDTO;
}
