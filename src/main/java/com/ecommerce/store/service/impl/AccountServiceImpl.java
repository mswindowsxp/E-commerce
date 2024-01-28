package com.ecommerce.store.service.impl;

import com.ecommerce.store.common.AppUtils;
import com.ecommerce.store.common.AuthenticationProvider;
import com.ecommerce.store.common.ResponseMessage;
import com.ecommerce.store.common.RoleConstant;
import com.ecommerce.store.config.security.JwtProvider;
import com.ecommerce.store.model.request.SignInRequest;
import com.ecommerce.store.model.request.SignUpRequest;
import com.ecommerce.store.model.response.ResponseModel;
import com.ecommerce.store.model.security.JwtResponse;
import com.ecommerce.store.model.security.Role;
import com.ecommerce.store.model.security.User;
import com.ecommerce.store.reposirtory.RoleRepository;
import com.ecommerce.store.reposirtory.UserRepository;
import com.ecommerce.store.service.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;


    @Override
    public ResponseModel<JwtResponse> signIn(SignInRequest signInRequest) {
        User user = userRepository.findByUsername(signInRequest.getUsername()).orElse(null);
        if (user == null) {
            return ResponseModel.failure("User Not Found");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getUsername(),
                        signInRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseModel.successful(HttpStatus.OK.toString(), new JwtResponse(jwt));
    }

    @Override
    public ResponseModel<?> signUp(SignUpRequest signUpRequest) {
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            return ResponseModel.failure("Those passwords did not match.");
        }

        if (!signUpRequest.getPassword().matches("\\S+$")) {
            return ResponseModel.failure("Password can not contain while space");
        }
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseModel.failure("Username is already exists, Please try again");
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseModel.failure("Email is already exists, Please try again");
        }

        User user = new User(
                signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEmail(),
                signUpRequest.getPhoneNumber(),
                signUpRequest.getFullName(),
                signUpRequest.getAddress()
        );
        Set<Role> roles = getRoles(signUpRequest);

        user.setRoles(roles);
        user.setAuthProvider(AuthenticationProvider.LOCAL);
        AppUtils.validatorUser(user);
        userRepository.save(user);
        return ResponseModel.successful(HttpStatus.OK.toString());
    }

    private Set<Role> getRoles(SignUpRequest signUpRequest) {
        Set<Role> roles = new HashSet<>();
        Set<String> roleInput = signUpRequest.getRoles();


        roleInput.forEach(role -> {
            if (role.equals("admin")) {
                Role adminRole = roleRepository.findByName(RoleConstant.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException(ResponseMessage.AUTH_ROLE_NOT_FIND.getMessage()));
                roles.add(adminRole);
            } else {
                Role userRole = roleRepository.findByName(RoleConstant.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException(ResponseMessage.AUTH_ROLE_NOT_FIND.getMessage()));
                roles.add(userRole);
            }
        });
        return roles;
    }

}
