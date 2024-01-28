package com.ecommerce.store.controller;

import com.ecommerce.store.common.ApiConstant;
import com.ecommerce.store.model.dto.BranchDTO;
import com.ecommerce.store.model.response.ResponseModel;
import com.ecommerce.store.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstant.ADMIN + ApiConstant.BRANCH)
@RequiredArgsConstructor
@Tag(name = "Branch management")
public class BranchController {

    private final BranchService branchService;

    @Operation(description = "List all branch")
    @GetMapping(ApiConstant.LIST)
    public ResponseModel<?> getAllBranch(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize,
                                         @RequestParam(value = "sortBy", required = false) String field, @RequestParam(value = "isDesc", required = false) Boolean isDesc) {
        return branchService.getAll(pageNumber, pageSize, field, isDesc);
    }

    @Operation(description = "Add new branch")
    @PostMapping(ApiConstant.ADD)
    public ResponseModel<?> addNewBranch(@RequestBody BranchDTO branchDTO) {
        return branchService.add(branchDTO);
    }


    @Operation(description = "delete branch by Id")
    @DeleteMapping(ApiConstant.DELETE + "/{id}")
    public ResponseModel<?> deleteBranch(@PathVariable("id") Long id) {
        return branchService.deleteById(id);
    }
}
