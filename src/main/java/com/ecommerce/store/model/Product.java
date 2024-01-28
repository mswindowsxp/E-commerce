package com.ecommerce.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product extends BaseEntity<Long> {

    @NotEmpty(message = "Product Name can not Empty")
    @NotNull(message = "Product Name Can not null")
    private String name;

    @Enumerated(EnumType.STRING)
    private Color color;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "product")
    private Quantity quantity;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
    private List<OrderDetail> orderDetails;

}
