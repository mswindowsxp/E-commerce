package com.ecommerce.store.model.mapper;

import com.ecommerce.store.model.OrderDetail;
import com.ecommerce.store.model.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<OrderDetail, OrderDTO> {
}
