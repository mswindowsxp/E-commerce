package com.ecommerce.store.model.mapper;

import com.ecommerce.store.model.Product;
import com.ecommerce.store.model.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product, ProductDTO> {
    @Override
    @Mapping(source = "quantity", target = "quantityDTO")
    ProductDTO toDTO(Product product);
}
