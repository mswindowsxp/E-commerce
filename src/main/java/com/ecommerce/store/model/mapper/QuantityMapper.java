package com.ecommerce.store.model.mapper;


import com.ecommerce.store.model.Quantity;
import com.ecommerce.store.model.dto.QuantityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuantityMapper extends BaseMapper<Quantity, QuantityDTO> {
}
