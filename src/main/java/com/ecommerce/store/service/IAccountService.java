package com.ecommerce.store.service;


import com.ecommerce.store.model.request.SignInRequest;
import com.ecommerce.store.model.request.SignUpRequest;
import com.ecommerce.store.model.response.ResponseModel;

public interface IAccountService {
    ResponseModel<?> signIn(SignInRequest signInRequest);

    ResponseModel<?> signUp(SignUpRequest signUpRequest);

}
