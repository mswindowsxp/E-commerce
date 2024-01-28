package com.ecommerce.store.model.mapper;

import com.ecommerce.store.model.Branch;
import com.ecommerce.store.model.dto.BranchDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchMapper extends BaseMapper<Branch, BranchDTO> {
}
