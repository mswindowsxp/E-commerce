package com.ecommerce.store.controller;

import com.ecommerce.store.common.ApiConstant;
import com.ecommerce.store.model.request.SignInRequest;
import com.ecommerce.store.model.request.SignUpRequest;
import com.ecommerce.store.model.response.ResponseModel;
import com.ecommerce.store.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Account controller")
@RequiredArgsConstructor
@RequestMapping(ApiConstant.AUTH)
public class AuthController {
    private final IAccountService accountService;

    @Operation(description = "Get token by username/password")
    @PostMapping(ApiConstant.SIGN_IN)
    public ResponseModel<?> signIn(@RequestBody SignInRequest signInRequest) {
        return accountService.signIn(signInRequest);
    }

    @Operation(description = "Register new account")
    @PostMapping(ApiConstant.SIGN_UP)
    public ResponseModel<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        return accountService.signUp(signUpRequest);
    }
}
