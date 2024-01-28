package com.ecommerce.store.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    @NotBlank
    @Size
            (min = 3, max = 60, message = "Username length must be more than 3 and less than 60 character")
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    @NotNull
    private String password;
}
